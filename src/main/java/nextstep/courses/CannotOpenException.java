package nextstep.courses;

public class CannotOpenException extends RuntimeException {
    public CannotOpenException(String message) {
        super(message);
    }
}
