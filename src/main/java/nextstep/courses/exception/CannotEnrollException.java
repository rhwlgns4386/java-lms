package nextstep.courses.exception;

public class CannotEnrollException extends RuntimeException {
    public CannotEnrollException(String message) {
        super(message);
    }
}
