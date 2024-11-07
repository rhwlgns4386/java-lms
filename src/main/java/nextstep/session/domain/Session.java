package nextstep.session.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import nextstep.enrollment.domain.Enrollment;

public class Session {
    private final Long id;
    private final Long courseId;
    private final String title;
    private final LocalDate startAt;
    private final LocalDate endAt;
    private final CoverImage image;
    private final SessionType sessionType;
    private final Long studentCapacity;
    private final Long sessionFee;
    private SessionProgressStatus sessionProgressStatus;
    private SessionEnrollmentStatus sessionEnrollmentStatus;
    private final Set<Enrollment> enrollments = new HashSet<>();

    public Session(Long id, Long courseId, String title, LocalDate startAt, LocalDate endAt, CoverImage image,
        SessionType sessionType, Long studentCapacity, Long sessionFee, SessionProgressStatus sessionProgressStatus,
        SessionEnrollmentStatus sessionEnrollmentStatus) {
        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.startAt = startAt;
        this.endAt = endAt;
        this.image = image;
        this.sessionType = sessionType;
        this.studentCapacity = studentCapacity;
        this.sessionFee = sessionFee;
        this.sessionProgressStatus = sessionProgressStatus;
        this.sessionEnrollmentStatus = sessionEnrollmentStatus;
    }

    private Session(Long id, Long courseId, String title, LocalDate startAt, LocalDate endAt, CoverImage image,
        SessionType sessionType, Long studentCapacity, Long sessionFee) {
        this(id, courseId, title, startAt, endAt, image, sessionType, studentCapacity, sessionFee,
            SessionProgressStatus.PREPARING, SessionEnrollmentStatus.NOT_RECRUITING);
    }

    public static Session createFreeSession(Long sessionId, String title, LocalDate startAt, LocalDate endAt,
        CoverImage image) {
        return new Session(1L, sessionId, title, startAt, endAt, image, SessionType.FREE, null, null);
    }

    public static Session createPaidSession(Long sessionId, String title, LocalDate startAt, LocalDate endAt,
        CoverImage image, Long studentCapacity, Long sessionFee) {
        return new Session(1L, sessionId, title, startAt, endAt, image, SessionType.PAID, studentCapacity, sessionFee);
    }

    public void startSession() {
        sessionProgressStatus = SessionProgressStatus.IN_PROGRESS;
    }

    public void startRecruitment() {
        if (sessionProgressStatus == SessionProgressStatus.COMPLETED) {
            throw new IllegalStateException("종료된 강의는 모집 상태를 변경할 수 없습니다.");
        }
        sessionEnrollmentStatus = SessionEnrollmentStatus.RECRUITING;
    }

    public void completeSession() {
        sessionProgressStatus = SessionProgressStatus.COMPLETED;
    }

    public void enroll(Enrollment enrollment) {
        validateRegister();
        enrollments.add(enrollment);
    }

    private void validateRegister() {
        if (sessionProgressStatus != SessionProgressStatus.IN_PROGRESS) {
            throw new IllegalStateException("모집중 상태의 강의가 아닙니다.");
        }
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

    public SessionProgressStatus getCourseStatus() {
        return sessionProgressStatus;
    }

    public int getEnrolledUserCount() {
        return enrollments.size();
    }

    public Long getStudentCapacity() {
        return studentCapacity;
    }

    public Long getSessionFee() {
        return sessionFee;
    }

    public SessionType getSessionType() {
        return sessionType;
    }
}
