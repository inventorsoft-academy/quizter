package com.quizter.util;

import org.zeroturnaround.exec.ProcessExecutor;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProjectRunner {

	public static String run() {
		String result = "";
		try {
			result = new ProcessExecutor().
					command("mvn", "-f", "/home/intern/chorney/backet/project/pom.xml", "test").
					readOutput(true).execute().outputUTF8();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return result;
	}
}
