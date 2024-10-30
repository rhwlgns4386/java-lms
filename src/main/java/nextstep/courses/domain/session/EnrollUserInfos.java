package nextstep.courses.domain.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EnrollUserInfos {
    private static final String ENROLL_USER_ERROR = "이미 수강 신청한 회원입니다.";
    private static final String ENROLL_CAPACITY_ERROR = "수강 신청 인원이 꽉차서 신청 할 수 없습니다.";

    private final List<EnrollUserInfo> enrollUserInfos = new ArrayList<>();
    private final int availableEnrollCount;

    public EnrollUserInfos(int availableEnrollCount) {
        this.availableEnrollCount = availableEnrollCount;
    }

    public int getSize() {
        return enrollUserInfos.size();
    }

    public List<EnrollUserInfo> getEnrollUserInfos() {
        return enrollUserInfos;
    }

    public void add(EnrollUserInfo enrollUserInfo) {
        isValid(enrollUserInfo);

        enrollUserInfos.add(enrollUserInfo);
    }

    private void isValid(EnrollUserInfo enrollUserInfo) {
        if (enrollUserInfos.contains(enrollUserInfo)) {
            throw new IllegalArgumentException(ENROLL_USER_ERROR);
        }

        if(getSize()==availableEnrollCount) {
            throw new RuntimeException(ENROLL_CAPACITY_ERROR);
        }

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
