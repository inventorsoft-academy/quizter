package com.quizter.entity.test;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.List;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResultAnswer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne
	@JoinColumn(name = "quiz_result_id")
	QuizResult quizResult;

	@OneToOne
	@JoinColumn(name = "question_id", referencedColumnName = "id")
	Question question;

	@ElementCollection
	@CollectionTable(name = "string_answers",
			joinColumns = {@JoinColumn(name = "result_answer_id", referencedColumnName = "id")})
	@Column(name = "string_answer")
	List<String> stringAnswers;

}
