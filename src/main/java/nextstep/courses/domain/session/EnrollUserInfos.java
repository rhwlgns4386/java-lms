package nextstep.courses.domain.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EnrollUserInfos {
    private static final String ENROLL_USER_ERROR = "이미 수강 신청한 회원입니다.";

    private List<EnrollUserInfo> enrollUserInfos;

    public EnrollUserInfos() {
        this(new ArrayList<>());
    }

    public EnrollUserInfos(List<EnrollUserInfo> enrollUserInfos) {
        this.enrollUserInfos = enrollUserInfos;
    }


    public int getSize() {
        return enrollUserInfos.size();
    }

    public List<EnrollUserInfo> getEnrollUserInfos() {
        return enrollUserInfos;
    }

    public void add(EnrollUserInfo enrollUserInfo) {
        if (enrollUserInfos.contains(enrollUserInfo)) {
            throw new IllegalArgumentException(ENROLL_USER_ERROR);
        }

        enrollUserInfos.add(enrollUserInfo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnrollUserInfos that = (EnrollUserInfos) o;
        return Objects.equals(enrollUserInfos, that.enrollUserInfos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enrollUserInfos);
    }

}
