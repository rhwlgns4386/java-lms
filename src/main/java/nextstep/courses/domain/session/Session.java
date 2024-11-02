package nextstep.courses.domain.session;

import java.time.LocalDate;

import nextstep.courses.utils.UUIDGenerator;

public class Session {
    private final String id;
    private final LocalDate startAt;
    private final LocalDate endAt;
    private final CoverImage image;
    private final PaymentPolicy paymentPolicy;
    private final EnrollmentCapacityPolicy enrollmentCapacityPolicy;
    private SessionStatus sessionStatus;
    private int enrollStudentCount;

    public Session(LocalDate startAt, LocalDate endAt, CoverImage image,
        EnrollmentCapacityPolicy enrollmentCapacityPolicy, PaymentPolicy paymentPolicy) {
        this.id = UUIDGenerator.getUUID();
        this.startAt = startAt;
        this.endAt = endAt;
        this.image = image;
        this.paymentPolicy = paymentPolicy;
        this.enrollmentCapacityPolicy = enrollmentCapacityPolicy;
        this.sessionStatus = SessionStatus.PENDING;
    }

    public static Session createFreeSession(LocalDate startAt, LocalDate endAt, CoverImage image) {
        return new Session(startAt, endAt, image, new UnlimitedCapacityPolicy(), PaymentPolicy.FREE);
    }

    public static Session createPaidSession(LocalDate startAt, LocalDate endAt, CoverImage image, int studentCapacity) {
        return new Session(startAt, endAt, image, new LimitedCapacityPolicy(studentCapacity), PaymentPolicy.PAID);
    }

    public SessionStatus getCourseStatus() {
        return sessionStatus;
    }

    public int getEnrollStudentCount() {
        return enrollStudentCount;
    }

    public void open() {
        sessionStatus = SessionStatus.OPEN;
    }

    public void register() {
        if (sessionStatus != SessionStatus.OPEN) {
            throw new IllegalArgumentException("모집중 상태의 강의가 아닙니다.");
        }
        if (!enrollmentCapacityPolicy.isApplicable(enrollStudentCount)) {
            throw new IllegalArgumentException("정원을 초과했습니다.");
        }
        enrollStudentCount++;
    }
}
