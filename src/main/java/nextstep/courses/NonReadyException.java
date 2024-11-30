package nextstep.courses;

public class NonReadyException extends IllegalStateException {
    public NonReadyException() {
        super("수강 신청기간이 아니라서 진행 할 수 없습니다");
    }
}
