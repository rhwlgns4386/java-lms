package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

import java.util.List;

public class PaidSession extends Session {
    private final int maxNumOfStudents;
    private final int sessionFee;

    public PaidSession(String sessionId, SessionDate date, SessionImage image, SessionStatus status, List<NsUser> students, int maxNumOfStudents, int sessionFee) {
        super(sessionId, date, image, status, students);
        if (maxNumOfStudents < students.size()) {
            throw new IllegalArgumentException("수강 정원이 초과됐습니다.");
        }
        this.maxNumOfStudents = maxNumOfStudents;
        this.sessionFee = sessionFee;
    }

    @Override
    public void enroll(NsUser user) {
        if (maxNumOfStudents <= students.size()) {
            throw new IllegalStateException("수강 정원이 초과됐습니다.");
        }
        super.enroll(user);
    }
}
