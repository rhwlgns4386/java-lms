package nextstep.courses.domain.session;

@FunctionalInterface
public interface EnrollmentCapacityPolicy {

    boolean isApplicable(int enrollStudentCount);

}
