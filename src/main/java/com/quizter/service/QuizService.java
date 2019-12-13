package com.quizter.service;

import com.quizter.dto.QuizResultDto;
import com.quizter.entity.QuizResult;
import com.quizter.entity.test.MultiVariantQuestion;
import com.quizter.repository.QuizResultRepository;
import com.quizter.repository.TestRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizService {

    TestRepository testRepository;
    QuizResultRepository quizResultRepository;
    UserService userService;
//
//    public long saveResult(long id, QuizResultDto quizResultDto) {
//        QuizResult quizResult = new QuizResult();
//        quizResult.setRightResults(getRightAnswers(id));
//        quizResult.setUser(userService.getUserPrincipal());
//        quizResult.setResults(quizResultDto.getAnswers());
//        quizResult.setTestName(testRepository.findById(id).orElseThrow().getName());
//        quizResultRepository.save(quizResult);
//        return getRating(quizResult.getTestName());
//    }
//
//    QuizResult getResult(String testName) {
//        return quizResultRepository.findByTestName(testName).orElseThrow();
//    }
//
//    long getRating(String testName) {
//        QuizResult quizResult = getResult(testName);
//        Map<String, String> answers = quizResult.getResults();
//        Map<String, String> rightAnswers = quizResult.getRightResults();
//        int numberOfQuestions = rightAnswers.size();
//        long count = 0;
//        for (Map.Entry<String, String> entry : answers.entrySet()) {
//            if (rightAnswers.get(entry.getKey()).equals(entry.getValue())) {
//                count++;
//            } else {
//                count--;
//            }
//            return count * 100 / numberOfQuestions;
//        }
//// TODO time? question value
//        public Map<String, String> getRightAnswers(long id) {
//            return testRepository.findById(id).orElseThrow().getQuestions()
//                    .stream()
//                    .collect(Collectors.toMap(MultiVariantQuestion::getName, question -> question.getAnswers().entrySet()
//                            .stream()
//                            .fi(answer -> answer.getValue().equals(true))
//
//                    ));
//        }
    }
