package com.like.util;

import java.util.Random;

public class ValidateCodeUtil {

	public static String getValidateCode() {
		Random random = new Random();
		String result = "";
		for (int i = 0; i < 6; i++) {
			result += random.nextInt(10);
		}
		return result;
	}
}
