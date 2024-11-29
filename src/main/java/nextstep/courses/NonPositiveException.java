package nextstep.courses;

public class NonPositiveException extends IllegalArgumentException {
    public NonPositiveException() {
        super("음수 일 수 없다");
    }
}
