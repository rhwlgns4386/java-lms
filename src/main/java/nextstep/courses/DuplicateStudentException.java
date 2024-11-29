package nextstep.courses;

public class DuplicateStudentException extends IllegalArgumentException {
    public DuplicateStudentException() {
        super("같은 강의에 중복으로 등록 할 수 없습니다.");
    }
}
