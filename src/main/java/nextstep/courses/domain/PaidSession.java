package nextstep.courses.domain;

public class PaidSession extends Session {
    private final int maxNumOfStudents;

    public PaidSession(SessionDate date, SessionImage image, SessionStatus status, int numOfStudents, int maxNumOfStudents) {
        super(date, image, status, numOfStudents);
        validateStudentNum(numOfStudents, maxNumOfStudents);
        this.maxNumOfStudents = maxNumOfStudents;
    }

    private static void validateStudentNum(int numOfStudents, int maxNumOfStudents) {
        if (maxNumOfStudents < numOfStudents) {
            throw new IllegalArgumentException("수강 정원이 초과됐습니다.");
        }
    }
}
