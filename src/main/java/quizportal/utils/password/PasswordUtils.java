package quizportal.utils.password;

import org.apache.commons.lang3.RandomStringUtils;
import quizportal.exception.PasswordMismatchException;
import quizportal.exception.PermissionDeniedException;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class PasswordUtils {

    private static final int TEMP_PASSWORD_LENGTH = 15;

    public static String getDisfiguredPassword(String password) {
        if (password.length() < 10) throw new PermissionDeniedException();
        byte[] passwordArr = password.substring(0, 10).getBytes(StandardCharsets.UTF_8);
        passwordArr = Base64.getEncoder().encode(passwordArr);
        Arrays.sort(passwordArr);
        return Base64.getEncoder().encodeToString(passwordArr);
    }

    public static String generateOneTimePassword() {
        return RandomStringUtils.randomAlphanumeric(TEMP_PASSWORD_LENGTH);
    }

    public static void comparePasswords(String passwordOne, String passwordTwo) {
        if (passwordOne.equals(passwordTwo)) {
            return;
        }

        throw new PasswordMismatchException();
    }
}
