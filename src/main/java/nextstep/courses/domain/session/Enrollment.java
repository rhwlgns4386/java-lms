package nextstep.courses.domain.session;

import java.time.LocalDateTime;

import nextstep.users.domain.NsUser;

public class Enrollment {
    private final long id;
    private final Session session;
    private final NsUser nsUser;
    private final LocalDateTime enrollmentDate;

    public Enrollment(long id, Session session, NsUser nsUser, LocalDateTime enrollmentDate) {
        this.id = id;
        this.session = session;
        this.nsUser = nsUser;
        this.enrollmentDate = enrollmentDate;
    }
}
