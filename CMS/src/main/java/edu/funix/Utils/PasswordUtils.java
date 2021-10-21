package edu.funix.Utils;

import java.util.Random;

public class PasswordUtils {
    public static String generate(int len) {
	/*
	 * This our Password generating method We have use static here, so that we not
	 * to make any object for it
	 */

	/*
	 * A strong password has Cap_chars, Lower_chars, numeric value and symbols. So
	 * we are using all of them to generate our password
	 */
	String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	String Small_chars = "abcdefghijklmnopqrstuvwxyz";
	String numbers = "0123456789";
	String symbols = "!@#$%&*_.?)";

	String values = Capital_chars + Small_chars + numbers + symbols;

	/* Using random method */
	Random rndm_method = new Random();
	char[] password = new char[len];
	password[0] = Capital_chars.charAt(rndm_method.nextInt(Capital_chars.length()));
	password[1] = numbers.charAt(rndm_method.nextInt(numbers.length()));
	for (int i = 2; i < len; i++) {
	    // Use of charAt() method : to get character value
	    // Use of nextInt() as it is scanning the value as int
	    password[i] = values.charAt(rndm_method.nextInt(values.length()));
	}
	return String.valueOf(password);
    }
}
