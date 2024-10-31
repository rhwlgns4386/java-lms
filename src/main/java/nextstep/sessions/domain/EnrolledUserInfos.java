package nextstep.sessions.domain;

import nextstep.registration.domain.SessionRegistrationInfo;
import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnrolledUserInfos {
    private List<SessionRegistrationInfo> infos;

    public EnrolledUserInfos() {
        this.infos = new ArrayList<>();
    }

    public EnrolledUserInfos(List<SessionRegistrationInfo> infos) {
        this.infos = infos;
    }

    public List<SessionRegistrationInfo> getInfos() {
        return Collections.unmodifiableList(infos);
    }

    public int size() {
        return infos.size();
    }

    public void add(SessionRegistrationInfo info) {
        infos.add(info);
    }

    public boolean isAlreadyEnrolled(NsUser user) {
        return infos.stream()
                .map(SessionRegistrationInfo::getUser)
                .anyMatch(u -> u.equals(user));
    }
}
