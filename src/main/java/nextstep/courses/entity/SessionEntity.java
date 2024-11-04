package nextstep.courses.entity;

import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.ProgressStatus;
import nextstep.courses.domain.RecruitingStatus;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionImage;
import nextstep.courses.domain.SessionSelection;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.List;

import static nextstep.courses.domain.ProgressStatus.PREPARING;
import static nextstep.courses.domain.RecruitingStatus.NON_RECRUITING;

public class SessionEntity {
    public static final String FREE_SESSION = "FREE";
    public static final String PAID_SESSION = "PAID";
    public static final int FREE_FEE = 0;
    public static final int FREE_MAX_STUDENTS = 0;
    public static final String BLANK = "";
    private Long id;
    private Timestamp sessionStartAt;
    private Timestamp sessionEndAt;
    private String status;
    private String recruitingStatus;
    private String progressStatus;
    private Long imageId;
    private String sessionType = FREE_SESSION;
    private Integer sessionFee = FREE_FEE;
    private Integer maxStudent = FREE_MAX_STUDENTS;
    private String sessionSelection;

    public SessionEntity() {
    }

    public SessionEntity(Long id, Timestamp sessionStartAt, Timestamp sessionEndAt, String status, String sessionType, Integer sessionFee, Integer maxStudent) {
        this(id, sessionStartAt, sessionEndAt, status, BLANK, BLANK, sessionType, sessionFee, maxStudent);
    }

    public SessionEntity(Long id, Timestamp sessionStartAt, Timestamp sessionEndAt, String status, String recruitingStatus, String progressStatus, String sessionType, Integer sessionFee, Integer maxStudent) {
        this.id = id;
        this.sessionStartAt = sessionStartAt;
        this.sessionEndAt = sessionEndAt;
        this.status = status;
        this.recruitingStatus = recruitingStatus;
        this.progressStatus = progressStatus;
        this.sessionType = sessionType;
        this.sessionFee = sessionFee;
        this.maxStudent = maxStudent;
    }

    public SessionEntity(Long id, Timestamp sessionStartAt, Timestamp sessionEndAt, String status, String recruitingStatus, String progressStatus, String sessionType, Integer sessionFee, Integer maxStudent, String sessionSelection) {
        this.id = id;
        this.sessionStartAt = sessionStartAt;
        this.sessionEndAt = sessionEndAt;
        this.status = status;
        this.recruitingStatus = recruitingStatus;
        this.progressStatus = progressStatus;
        this.sessionType = sessionType;
        this.sessionFee = sessionFee;
        this.maxStudent = maxStudent;
        this.sessionSelection = sessionSelection;
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
        entity.recruitingStatus = session.getRecruitingStatus().name();
        entity.progressStatus = session.getProgressStatus().name();
        if (session.getImage() != null) {
            entity.imageId = session.getImage().getId();
        }
        if (session.getSessionSelection() != null) {
            entity.sessionSelection = session.getSessionSelection().name();
        }
        return entity;
    }

    public Session to(SessionImage sessionImage, List<Long> sessionStudents) {
        if (!StringUtils.hasText(recruitingStatus)) {
            recruitingStatus = NON_RECRUITING.name();
        }
        if (!StringUtils.hasText(progressStatus)) {
            progressStatus = PREPARING.name();
        }
        if (sessionType.equals(PAID_SESSION)) {
            return new PaidSession(id, sessionStartAt, sessionEndAt, sessionImage, RecruitingStatus.valueOf(recruitingStatus), ProgressStatus.valueOf(progressStatus), sessionStudents, maxStudent, sessionFee);
        }
        return new FreeSession(id, sessionStartAt, sessionEndAt, sessionImage, RecruitingStatus.valueOf(recruitingStatus), ProgressStatus.valueOf(progressStatus), sessionStudents);
    }

    public Session toNew(List<SessionImage> sessionImages, List<Long> approvedStudents, List<Long> applyStudents) {
        if (!StringUtils.hasText(recruitingStatus)) {
            recruitingStatus = NON_RECRUITING.name();
        }
        if (!StringUtils.hasText(progressStatus)) {
            progressStatus = PREPARING.name();
        }
        SessionSelection sessionSelection = null;
        if (StringUtils.hasText(this.sessionSelection)) {
            sessionSelection = SessionSelection.valueOf(this.sessionSelection);
        }
        if (sessionType.equals(PAID_SESSION)) {
            return new PaidSession(id, sessionStartAt, sessionEndAt, sessionImages, RecruitingStatus.valueOf(recruitingStatus), ProgressStatus.valueOf(progressStatus), approvedStudents, applyStudents, maxStudent, sessionFee, sessionSelection);
        }
        return new FreeSession(id, sessionStartAt, sessionEndAt, sessionImages, RecruitingStatus.valueOf(recruitingStatus), ProgressStatus.valueOf(progressStatus), approvedStudents, applyStudents, sessionSelection);
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

    public String getRecruitingStatus() {
        return recruitingStatus;
    }

    public String getProgressStatus() {
        return progressStatus;
    }

    public String getSessionSelection() {
        return sessionSelection;
    }
}
