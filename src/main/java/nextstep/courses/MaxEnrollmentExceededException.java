package nextstep.courses;

public class MaxEnrollmentExceededException extends IllegalArgumentException {
    public MaxEnrollmentExceededException() {
        super("수강인원이 최대에 도달하였습니다");
    }
}
