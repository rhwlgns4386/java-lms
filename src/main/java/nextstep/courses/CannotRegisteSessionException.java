package nextstep.courses;

public class CannotRegisteSessionException extends Exception {
    private static final long serialVersionUID = 1L;

    public CannotRegisteSessionException(String message) {
        super(message);
    }
}
