package com.quizter;

import org.junit.jupiter.api.Test;
import org.postgresql.util.ReaderInputStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ProjReaderTest {

	@Test
	void read() {
		try {

			Files.write(Paths.get("/home/intern/chorney/backet/project/src/main/java/Foo.java"), "codeQuestion.getCodeTask()".getBytes());
			Files.write(Paths.get("/home/intern/chorney/backet/project/src/test/java/FooTest.java"), "codeQuestion.getUnitTest()".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}