package com.quizter.util;

import org.zeroturnaround.exec.ProcessExecutor;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProjReader {
	public static void reader() throws InterruptedException, TimeoutException, IOException {
		System.out.println(new ProcessExecutor().command("mvn", "-f", "/home/intern/chorney/backet/project", "test").readOutput(true).execute().outputUTF8());

	}
}
