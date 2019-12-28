package com.quizter.util;

import org.zeroturnaround.zip.ZipUtil;

import java.io.File;

public class UnZipUtil {
	public static void unZip(){
		ZipUtil.unpack(new File("src/main/resources/project.zip"), new File("/home/intern/chorney/backet"));
	}
}
