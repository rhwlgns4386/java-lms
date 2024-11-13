package nextstep.session.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Session {
    private final Long id;
    private final Long courseId;
    private final String title;
    private final LocalDate startAt;
    private final LocalDate endAt;
    private List<CoverImage> coverImages = new ArrayList<>();
    private final SessionType sessionType;
    private final Long studentCapacity;
    private final Long sessionFee;
    private SessionProgressStatus sessionProgressStatus;
    private SessionEnrollmentStatus sessionEnrollmentStatus;

    public Session(Long id, Long courseId, String title, LocalDate startAt, LocalDate endAt,
        List<CoverImage> coverImages,
        SessionType sessionType, Long studentCapacity, Long sessionFee, SessionProgressStatus sessionProgressStatus,
        SessionEnrollmentStatus sessionEnrollmentStatus) {
        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.startAt = startAt;
        this.endAt = endAt;
        this.coverImages = coverImages;
        this.sessionType = sessionType;
        this.studentCapacity = studentCapacity;
        this.sessionFee = sessionFee;
        this.sessionProgressStatus = sessionProgressStatus;
        this.sessionEnrollmentStatus = sessionEnrollmentStatus;
    }

    private Session(Long id, Long courseId, String title, LocalDate startAt, LocalDate endAt,
        List<CoverImage> coverImages,
        SessionType sessionType, Long studentCapacity, Long sessionFee) {
        this(id, courseId, title, startAt, endAt, coverImages, sessionType, studentCapacity, sessionFee,
            SessionProgressStatus.PREPARING, SessionEnrollmentStatus.NOT_RECRUITING);
    }

    public static Session createFreeSession(Long sessionId, String title, LocalDate startAt, LocalDate endAt,
        List<CoverImage> coverImages) {
        return new Session(1L, sessionId, title, startAt, endAt, coverImages, SessionType.FREE, null, null);
    }

    public static Session createPaidSession(Long sessionId, String title, LocalDate startAt, LocalDate endAt,
        List<CoverImage> coverImages, Long studentCapacity, Long sessionFee) {
        return new Session(1L, sessionId, title, startAt, endAt, coverImages, SessionType.PAID, studentCapacity,
            sessionFee);
    }

    // 강의 진행 상태를 진행중으로 바꾸는 메서드
    public void startSession() {
        sessionProgressStatus = SessionProgressStatus.IN_PROGRESS;
    }

    // 강의 진행 상태를 종료로 바꾸는 메서드
    public void completeSession() {
        sessionProgressStatus = SessionProgressStatus.COMPLETED;
    }

    // 강의 모집 상태를 모집중으로 바꾸는 메서드
    public void startRecruitment() {
        if (sessionProgressStatus == SessionProgressStatus.COMPLETED) {
            throw new IllegalStateException("종료된 강의는 모집 상태를 변경할 수 없습니다.");
        }
        sessionEnrollmentStatus = SessionEnrollmentStatus.RECRUITING;
    }

    public boolean isFree() {
        return sessionType == SessionType.FREE;
    }

    public boolean isRecruiting() {
        return sessionEnrollmentStatus == SessionEnrollmentStatus.RECRUITING;
    }

    public Long getId() {
        return id;
    }

    public SessionProgressStatus getCourseStatus() {
        return sessionProgressStatus;
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

    public List<CoverImage> getCoverImages() {
        return coverImages;
    }
}
