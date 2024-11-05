package nextstep.courses.exception;

public class CannotEnrollmentException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CannotEnrollmentException(String message) {
        super(message);
    }
}
