package quizportal.utils;

import java.security.SecureRandom;

public class RandomStringGenerator {

    private static final String AB = "123456789ABCDEFGHIJKLMNPQRSTUVWXYZabcdefghijklmnpqrstuvwxyz";
    private static final String NUMERIC = "0123456789";
    private static final SecureRandom rnd = new SecureRandom();

    public static String generateString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    public static String generateNumericString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(NUMERIC.charAt(rnd.nextInt(NUMERIC.length())));
        }
        return sb.toString();
    }
}