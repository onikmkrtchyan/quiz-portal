package quizportal.exception;

public class PermissionDeniedException extends RuntimeException {
    public PermissionDeniedException() {
        super("Unauthorized: Permission denied exception");
    }
}
