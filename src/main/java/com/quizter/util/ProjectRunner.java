package com.quizter.util;

import lombok.extern.slf4j.Slf4j;
import org.zeroturnaround.exec.ProcessExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
public class ProjectRunner {

	private static final String PATH_TO_TEST_PROJECT = "/home/intern/chorney/backet/project/pom.xml";
	private static final int SECONDS_TO_FINISH_WORK = 10;

	public static String run() {
		String result = "";

		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<String> future = executor.submit((Callable) () -> new ProcessExecutor().
				command("mvn", "-f", PATH_TO_TEST_PROJECT, "test").
				readOutput(true).execute().outputUTF8());
		try {
			result = future.get(SECONDS_TO_FINISH_WORK, TimeUnit.SECONDS);
		} catch (TimeoutException | InterruptedException e) {
			result = "ERROR";
			executor.shutdownNow();
		} catch (ExecutionException e) {
			log.error("IOException " + e);
		}
		executor.shutdownNow();
		return result;
	}
}
