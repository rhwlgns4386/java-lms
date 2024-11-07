package nextstep.courses.domain.enroll;

import nextstep.courses.domain.session.SessionPriceType;

import java.util.*;

public class EnrollUserInfos {
    private static final String FULL_CAPACITY_ERROR = "수강 정원이 모두 찼습니다.";
    private static final String ENROLL_USER_ERROR = "이미 수강 신청한 회원입니다.";

    private final Set<EnrollUserInfo> enrollUserInfos;
    private final int availableEnrollCount;

    public EnrollUserInfos(int availableEnrollCount) {
        this.enrollUserInfos = new HashSet<>();
        this.availableEnrollCount = availableEnrollCount;
    }

    public int getSize() {
        return enrollUserInfos.size();
    }

    public Set<EnrollUserInfo> getEnrollUserInfos() {
        return enrollUserInfos;
    }

    public void add(EnrollUserInfo enrollUserInfo) {
        int prevEnrollCount = enrollUserInfos.size();
        enrollUserInfos.add(enrollUserInfo);
        isValid(prevEnrollCount);
    }

    private void isValid(int prevEnrollCount) {
        if (prevEnrollCount == enrollUserInfos.size()) {
            throw new IllegalArgumentException(ENROLL_USER_ERROR);
        }

    }

    public void checkPaidSessionEnroll(SessionPriceType sessionPriceType) {
        if (!sessionPriceType.isFree()) {
            checkSize();
        }
    }

    private void checkSize() {
        if (enrollUserInfos.size() == availableEnrollCount) {
            throw new IllegalArgumentException(FULL_CAPACITY_ERROR);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EnrollUserInfos that = (EnrollUserInfos) o;
        return Objects.equals(enrollUserInfos, that.enrollUserInfos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enrollUserInfos);
    }


}
