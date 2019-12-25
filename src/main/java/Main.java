import com.quizter.dto.test.QuizResultDto;

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		List<String> quizResultDtos = new ArrayList<>();
		quizResultDtos.add(new QuizResultDto().toString());
		System.out.println(quizResultDtos);
	}
}
