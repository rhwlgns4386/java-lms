package nextstep.courses.exception;

public class CannotIncreaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CannotIncreaseException(String message) {
        super(message);
    }
}
