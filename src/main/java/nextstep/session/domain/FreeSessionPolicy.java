package nextstep.session.domain;

public class FreeSessionPolicy implements SessionPolicy {

    @Override
    public void validatePolicy(int enrollStudentCount, Enrollment enrollment) {
        // 무료강의는 정책이 존재하지 않음
    }
}
