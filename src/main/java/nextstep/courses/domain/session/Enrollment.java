package nextstep.courses.domain.session;

import nextstep.users.domain.NsUser;

import java.util.Objects;

public class Enrollment {

    private final NsUser user;
    private final Session session;

    public Enrollment(NsUser user, Session session) {
        this.user = user;
        this.session = session;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Enrollment)) return false;
        Enrollment that = (Enrollment) o;
        return Objects.equals(user, that.user) && Objects.equals(session, that.session);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, session);
    }
}
