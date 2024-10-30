package nextstep.courses.domain.session;

public class Enrollment {

    static final int INFINITE_ENROLLMENT = -1;

    private int enrollment;
    private final int maxEnrollment;

    public Enrollment(int maxEnrollment) {
        this.maxEnrollment = maxEnrollment;
    }

    public void validateAvailability() {
        if (isNotAvailable()) {
            throw new IllegalStateException("강의 수강 가능 인원이 초과되었습니다");
        }
    }

    public boolean isNotAvailable() {
        return maxEnrollment != INFINITE_ENROLLMENT && enrollment >= maxEnrollment;
    }

    public void register() {
        validateAvailability();
        enrollment++;
    }
}
