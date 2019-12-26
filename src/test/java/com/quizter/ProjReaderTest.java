package com.quizter;

import com.quizter.util.ProjectRunner;
import org.junit.jupiter.api.Test;
import org.postgresql.util.ReaderInputStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ProjReaderTest {

	@Test
	void read() {
			ProjectRunner.run();
	}
}