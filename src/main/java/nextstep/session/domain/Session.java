package nextstep.session.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Session {
    private final Long id;
    private final Long sessionId;
    private final String title;
    private final LocalDate startAt;
    private final LocalDate endAt;
    private final CoverImage image;
    private final Long sessionFee;
    private final SessionType sessionType;
    private final SessionPolicy sessionPolicy;
    private SessionStatus sessionStatus = SessionStatus.PENDING;
    private final Set<Enrollment> enrollments = new HashSet<>();

    public Session(Long id, Long sessionId, String title, LocalDate startAt, LocalDate endAt, CoverImage image,
        Long sessionFee, SessionType sessionType, SessionPolicy sessionPolicy) {
        this.id = id;
        this.sessionId = sessionId;
        this.title = title;
        this.startAt = startAt;
        this.endAt = endAt;
        this.image = image;
        this.sessionFee = sessionFee;
        this.sessionType = sessionType;
        this.sessionPolicy = sessionPolicy;
    }

    public static Session createFreeSession(Long sessionId, String title, LocalDate startAt, LocalDate endAt,
        CoverImage image) {
        return new Session(1L, sessionId, title, startAt, endAt, image, 0L, SessionType.FREE, new FreeSessionPolicy());
    }

    public static Session createPaidSession(Long sessionId, String title, LocalDate startAt, LocalDate endAt,
        CoverImage image, int studentCapacity, Long sessionFee) {
        return new Session(1L, sessionId, title, startAt, endAt, image, sessionFee, SessionType.PAID,
            new PaidSessionPolicy(studentCapacity, sessionFee));
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
        sessionPolicy.validatePolicy(getEnrolledUserCount(), enrollment);
    }

    public boolean isFree() {
        return sessionType == SessionType.FREE;
    }

    public boolean isPaid() {
        return sessionType == SessionType.PAID;
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
