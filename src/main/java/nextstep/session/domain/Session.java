package nextstep.session.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Session {
    private final Long id;
    private final LocalDate startAt;
    private final LocalDate endAt;
    private final CoverImage image;
    private final Long sessionFee;
    private final SessionPolicy sessionPolicy;
    private SessionStatus sessionStatus;
    private final Set<Enrollment> enrollments = new HashSet<>();

    public Session(Long id, LocalDate startAt, LocalDate endAt, CoverImage image, Long sessionFee,
        SessionPolicy sessionPolicy) {
        this.id = id;
        this.startAt = startAt;
        this.endAt = endAt;
        this.image = image;
        this.sessionPolicy = sessionPolicy;
        this.sessionFee = sessionFee;
        this.sessionStatus = SessionStatus.PENDING;
    }

    public static Session createFreeSession(LocalDate startAt, LocalDate endAt, CoverImage image) {
        return new Session(1L, startAt, endAt, image, 0L, new FreeSessionPolicy());
    }

    public static Session createPaidSession(LocalDate startAt, LocalDate endAt, CoverImage image,
        int studentCapacity, Long sessionFee) {
        return new Session(1L, startAt, endAt, image, sessionFee, new PaidSessionPolicy(studentCapacity, sessionFee));
    }

    public void open() {
        sessionStatus = SessionStatus.OPEN;
    }

    public void enroll(Enrollment enrollment) {
        validateRegister(enrollment);
        enrollments.add(enrollment);
    }

    private void validateRegister(Enrollment enrollment) {
        if (sessionStatus != SessionStatus.OPEN) {
            throw new IllegalArgumentException("모집중 상태의 강의가 아닙니다.");
        }
        sessionPolicy.validatePolicy(getEnrolledUserCount(), enrollment.getPaymentAmount());
    }

    public boolean isFree() {
        return sessionPolicy.getSessionPaymentType() == SessionPaymentType.FREE;
    }

    public Long getId() {
        return id;
    }

    public SessionStatus getCourseStatus() {
        return sessionStatus;
    }

    public int getEnrolledUserCount() {
        return enrollments.size();
    }
}
