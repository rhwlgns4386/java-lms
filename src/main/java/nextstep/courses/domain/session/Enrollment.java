package nextstep.courses.domain.session;

import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Enrollment {

    private final List<NsUser> users;
    private final SessionCapacity capacity;

    public Enrollment(int capacity) {
        this(new ArrayList<>(), new SessionCapacity(capacity));
    }

    public Enrollment(List<NsUser> users, int capacity) {
        this(users, new SessionCapacity(capacity));
    }

    public Enrollment(List<NsUser> users, SessionCapacity capacity) {
        this.users = users;
        this.capacity = capacity;
    }

    public void enroll(NsUser user) {
        capacity.increase();
        this.users.add(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Enrollment)) return false;
        Enrollment that = (Enrollment) o;
        return Objects.equals(users, that.users);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(users);
    }
}
