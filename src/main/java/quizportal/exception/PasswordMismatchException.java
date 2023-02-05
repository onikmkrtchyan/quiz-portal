package quizportal.exception;

public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException() {
        super("Password Mismatch");
    }
}
