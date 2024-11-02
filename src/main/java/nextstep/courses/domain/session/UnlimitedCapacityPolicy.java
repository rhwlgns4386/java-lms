package nextstep.courses.domain.session;

public class UnlimitedCapacityPolicy implements EnrollmentCapacityPolicy {

    @Override
    public boolean isApplicable(int count) {
        return true;
    }

}
