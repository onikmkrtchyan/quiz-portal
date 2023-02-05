package quizportal.exception;

public class RefreshTokenNotFoundException extends RuntimeException {
    public RefreshTokenNotFoundException(String token) {
        super("Refresh Token not in DB  " + token);
    }
}
