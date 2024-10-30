package nextstep.courses.domain.session;

import java.time.LocalDate;

public class Session {
    private Long id;

    private LocalDate startAt;

    private LocalDate endAt;

    private CoverImage image;

    private PaymentPolicy paymentPolicy;

    private SessionStatus sessionStatus;

    private StudentCapacity studentCapacity;

    private int enrollStudentCount;

    public Session(LocalDate startAt, LocalDate endAt, CoverImage image, StudentCapacity studentCapacity,
        PaymentPolicy paymentPolicy) {
        this.startAt = startAt;
        this.endAt = endAt;
        this.image = image;
        this.paymentPolicy = paymentPolicy;
        this.studentCapacity = studentCapacity;
        this.sessionStatus = SessionStatus.PENDING;
    }

    public static Session createFreeSession(LocalDate startAt, LocalDate endAt, CoverImage image) {
        return new Session(startAt, endAt, image, new UnlimitedCapacity(), PaymentPolicy.FREE);
    }

    public static Session createPaidSession(LocalDate startAt, LocalDate endAt, CoverImage image, int studentCapacity) {
        return new Session(startAt, endAt, image, new LimitedCapacity(studentCapacity), PaymentPolicy.PAID);
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
        if (!studentCapacity.isApplicable(enrollStudentCount)) {
            throw new IllegalArgumentException("정원을 초과했습니다.");
        }
        enrollStudentCount++;
    }
}
