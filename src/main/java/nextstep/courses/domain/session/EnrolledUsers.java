package nextstep.courses.domain.session;

import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnrolledUsers {
    private List<NsUser> users;

    public EnrolledUsers() {
        this.users = new ArrayList<>();
    }

    public List<NsUser> getUsers() {
        return Collections.unmodifiableList(users);
    }

    public int size() {
        return users.size();
    }

    public void add(NsUser user) {
        users.add(user);
    }

    public boolean isAlreadyEnrolled(NsUser user) {
        return users.contains(user);
    }
}
