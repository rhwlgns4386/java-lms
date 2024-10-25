package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

import java.util.List;

public class FreeSession extends Session {


    public FreeSession(String sessionId, SessionDate date, SessionImage image, SessionStatus status, List<NsUser> numOfStudents) {
        super(sessionId, date, image, status, numOfStudents);
    }

    @Override
    public void enroll(NsUser user) {
        super.enroll(user);
    }
}
