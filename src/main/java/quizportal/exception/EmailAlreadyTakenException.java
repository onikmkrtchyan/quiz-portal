package quizportal.exception;

public class EmailAlreadyTakenException extends RuntimeException {
    public EmailAlreadyTakenException(String email) {
        super("For this Email already exists account " + email);
    }
}
