package com.quizter.service.test;

import com.quizter.dictionary.QuestionType;
import com.quizter.dto.test.QuizResultDto;
import com.quizter.entity.User;
import com.quizter.entity.test.CodeQuestion;
import com.quizter.entity.test.MultiVariantQuestion;
import com.quizter.entity.test.Question;
import com.quizter.entity.test.QuizResult;
import com.quizter.entity.test.ResultAnswer;
import com.quizter.entity.test.Test;
import com.quizter.exception.UserIsNotAuthorizedException;
import com.quizter.mapper.test.ResultMapper;
import com.quizter.mapper.test.TestMapper;
import com.quizter.repository.QuizResultRepository;
import com.quizter.repository.ResultAnswerRepository;
import com.quizter.service.UserService;
import com.quizter.util.ProjectRunner;
import com.quizter.util.UnZipUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Transactional
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizResultService {

	UserService userService;
	TestService testService;
	TestMapper testMapper;
	ResultMapper resultMapper;
	QuizResultRepository quizResultRepository;
	ResultAnswerRepository resultAnswerRepository;
	List<TestQuestionEvaluator> evaluators;

	public Optional<QuizResult> findById(String quizResultId) {
		return quizResultRepository.findById(quizResultId);
	}

	private String createQuizResultId() {
		String quizResultId = UUID.randomUUID().toString();
		if (quizResultRepository.findById(quizResultId).isPresent()) {
			createQuizResultId();
		}
		return quizResultId;
	}

	public void addAccessToTest(User applicant, Long testId) {
		QuizResult quizResult = new QuizResult();

		quizResult.setId(createQuizResultId());
		quizResult.setIsCompleted(false);
		quizResult.setApplicant(applicant);
		Test test = testMapper.toTest(testService.findTestById(testId));
		quizResult.setTest(test);

		quizResultRepository.save(quizResult);
	}

	public String beginQuiz(Long testId) {
		QuizResult quizResult = quizResultRepository.findQuizResultByTestId(testId);

		quizResult.setStart(Instant.now());
		long minutes = quizResult.getTest().getDuration();
		quizResult.setFinished(Instant.now().plus(Duration.ofMinutes(minutes)));

		return quizResultRepository.save(quizResult).getId();
	}

	public List<Test> getTestAccessibleTestsForStudent() {
		List<Test> tests = new ArrayList<>();
		quizResultRepository.findAll().forEach(quizResult -> tests.add(quizResult.getTest()));

		return tests;
	}

	public void updateQuiz(String quizResultId, List<QuizResultDto> quizResultDtos) {
		QuizResult quizResult = quizResultRepository.findById(quizResultId).orElseThrow();
		quizResult.setResultAnswers(getResultAnswers(quizResult, quizResultDtos));
		quizResultRepository.save(quizResult);
	}

	private List<ResultAnswer> getResultAnswers(QuizResult quizResult, List<QuizResultDto> quizResultDtos) {
		final ResultAnswer[] resultAnswers = new ResultAnswer[1];
		return quizResultDtos.stream().map(dto -> {
			resultAnswers[0] = resultMapper.quizResultDtoToAnswer(dto);
			resultAnswers[0].setQuizResult(quizResult);
			if (resultAnswerRepository.findByQuizResultIdAndQuestionId(quizResult.getId(), resultAnswers[0].getQuestion().getId()).isPresent()) {
				resultAnswers[0]
						.setId(resultAnswerRepository.findByQuizResultIdAndQuestionId(quizResult.getId(), resultAnswers[0].getQuestion().getId()).orElseThrow()
								.getId());
			}
			resultAnswerRepository.save(resultAnswers[0]);
			return resultAnswers[0];
		}).collect(Collectors.toList());
	}

	public Double finishQuiz(String quizResultId, List<QuizResultDto> quizResultDtos) {
		QuizResult quizResult = quizResultRepository.findById(quizResultId).orElseThrow();
		quizResult.setFinished(Instant.now());
		quizResult.setResultAnswers(getResultAnswers(quizResult, quizResultDtos));
		quizResult.setIsCompleted(true);
		double totalRating = evaluate(quizResult);
		quizResult.setTotalRating(totalRating);
		quizResultRepository.save(quizResult);
		return totalRating;
	}

	public Long getDuration(String quizResultId) {
		QuizResult quizResult = findById(quizResultId).orElseThrow();
		return (quizResult.getFinished().getEpochSecond() - Instant.now().getEpochSecond());
	}

	public double evaluate(QuizResult quizResult) {
		isCodeAnswerCorrect(quizResult);
		final Double maxPrice = quizResult.getResultAnswers().stream()
				.filter(answer -> QuestionType.MULTIVARIANT.equals(answer.getQuestion().getQuestionType())).map(answer -> answer.getQuestion().getPrice())
				.reduce(Double::sum).orElse(100.0);
		double totalPrice = 0;
		List<ResultAnswer> resultAnswers = quizResult.getResultAnswers().stream()
				.filter(answer -> QuestionType.MULTIVARIANT.equals(answer.getQuestion().getQuestionType())).collect(Collectors.toList());
		for (ResultAnswer answer : resultAnswers) {
			MultiVariantQuestion question = (MultiVariantQuestion) answer.getQuestion();
			double price = answer.getQuestion().getPrice();
			List<String> rightAnswers = question.getAnswers().entrySet().stream().filter(qAnswer -> qAnswer.getValue().equals(true)).map(Map.Entry::getKey)
					.collect(Collectors.toList());
			long quantity = answer.getStringAnswers().stream().filter(rightAnswers::contains).count();
			if (quantity == answer.getStringAnswers().size()) {
				if (quantity == rightAnswers.size()) {
					totalPrice += price;
				} else if (rightAnswers.size() > 1 && quantity < rightAnswers.size() && quantity > 0) {
					totalPrice += price * quantity / rightAnswers.size();
				}
			}
		}
		totalPrice += evaluateCode(quizResult);
		return totalPrice * 100 / maxPrice;
	}

	private boolean isCodeAnswerCorrect(QuizResult quizResult) {

		String answer = quizResult.getResultAnswers().stream().filter(resultAnswer -> resultAnswer.getQuestion().getQuestionType().equals(QuestionType.CODE))
				.findFirst().orElseThrow().getStringAnswers().stream().findFirst().orElseThrow();
		String unitTest = quizResult.getTest().getQuestions().stream().filter(question -> question.getQuestionType().equals(QuestionType.CODE))
				.map(question -> (CodeQuestion) question).findFirst().orElseThrow().getUnitTest();
		writeCodingQuestionIntoClass(answer, unitTest);
		return !ProjectRunner.run().contains("ERROR");
	}

	private double evaluateCode(QuizResult quizResult) {
		return isCodeAnswerCorrect(quizResult) ? quizResult.getTest().getQuestions().stream().findFirst().orElseThrow().getPrice() : 0;
	}

	private void writeCodingQuestionIntoClass(String answer, String unitTest) {
		try {

			Files.write(Paths.get("/home/intern/chorney/backet/project/src/main/java/Foo.java"), answer.getBytes());
			Files.write(Paths.get("/home/intern/chorney/backet/project/src/test/java/FooTest.java"), unitTest.getBytes());
		} catch (NoSuchFileException noSuchFileException) {
			UnZipUtil.unZip();
		} catch (IOException e) {
			log.debug(e.getMessage());
		}

	}

	public List<QuizResult> findByApplicant() {
		User applicant = userService.getUserPrincipal().orElseThrow(UserIsNotAuthorizedException::new);
		return quizResultRepository.findAllByApplicantAndIsCompleted(applicant, true);
	}

	public double evaluateResult(final QuizResult quizResult, Map<Long, String> answers) {
		List<Question> questions = quizResult.getTest().getQuestions();
		Map<Long, Question> questionById = questions.stream().collect(Collectors.toMap(Question::getId, Function.identity()));
		Map<Long, Double> resultAnswers = new HashMap<>();
		answers.forEach((questionId, answer) -> {
			Question question = questionById.get(questionId);
			TestQuestionEvaluator evaluator = evaluators.stream().filter(eval -> eval.isApplicable(question)).findFirst()
					.orElseThrow(() -> new IllegalStateException("Unsupported question type"));
			resultAnswers.put(questionId, evaluator.evaluate(question, answer));
		});
		return resultAnswers.values().stream().mapToDouble(Double::doubleValue).sum();
	}

	@Component
	private static final class StandardEvaluator implements TestQuestionEvaluator {
		@Override
		public boolean isApplicable(Question question) {
			return question instanceof MultiVariantQuestion;
		}

		@Override
		public double evaluate(Question question, String answer) {
			return MultiVariantQuestion.class.cast(question).getAnswers().get(answer) ? question.getPrice() : 0.0;
		}
	}
}
