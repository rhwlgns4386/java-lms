package nextstep.courses.entity;

import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionImage;

import java.sql.Timestamp;
import java.util.List;

public class SessionEntity {
    public static final String FREE_SESSION = "FREE";
    public static final String PAID_SESSION = "PAID";
    public static final int FREE_FEE = 0;
    public static final int FREE_MAX_STUDENTS = 0;
    private Long id;
    private Timestamp sessionStartAt;
    private Timestamp sessionEndAt;
    private String status;
    private Long imageId;
    private String sessionType = FREE_SESSION;
    private Integer sessionFee = FREE_FEE;
    private Integer maxStudent = FREE_MAX_STUDENTS;

    public SessionEntity() {
    }

    public SessionEntity(Long id, Timestamp sessionStartAt, Timestamp sessionEndAt, String status, String sessionType, Integer sessionFee, Integer maxStudent) {
        this.id = id;
        this.sessionStartAt = sessionStartAt;
        this.sessionEndAt = sessionEndAt;
        this.status = status;
        this.sessionType = sessionType;
        this.sessionFee = sessionFee;
        this.maxStudent = maxStudent;
    }

    public static SessionEntity from(Session session) {
        SessionEntity entity = new SessionEntity();
        if (session instanceof PaidSession) {
            PaidSession paidSession = (PaidSession) session;
            entity.sessionType = PAID_SESSION;
            entity.maxStudent = paidSession.getMaxNumOfStudents();
            entity.sessionFee = paidSession.getSessionFee();
        }
        entity.id = session.getSessionId();
        entity.sessionStartAt = Timestamp.valueOf(session.getDate().getStartAt());
        entity.sessionEndAt = Timestamp.valueOf(session.getDate().getEndAt());
        entity.status = session.getStatus().name();
        entity.imageId = session.getImage().getId();
        return entity;
    }

    public Session toDomain(SessionImage sessionImage, List<Long> sessionStudents) {
        if (sessionType.equals(PAID_SESSION)) {
            return new PaidSession(id, sessionStartAt, sessionEndAt, sessionImage, status, sessionStudents, maxStudent, sessionFee);
        }
        return new FreeSession(id, sessionStartAt, sessionEndAt, sessionImage, status, sessionStudents);
    }

    public Long getId() {
        return id;
    }

    public Timestamp getSessionStartAt() {
        return sessionStartAt;
    }

    public Timestamp getSessionEndAt() {
        return sessionEndAt;
    }

    public String getStatus() {
        return status;
    }

    public Long getImageId() {
        return imageId;
    }

    public String getSessionType() {
        return sessionType;
    }

    public Integer getSessionFee() {
        return sessionFee;
    }

    public Integer getMaxStudent() {
        return maxStudent;
    }
}
