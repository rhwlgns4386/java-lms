package nextstep.sessions.domain;

import nextstep.courses.domain.Course;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Session {
    private Long id;
    private Course course;
    private SessionImage sessionImage;
    private SessionPeriod sessionPeriod;
    private SessionType sessionType;
    private SessionStatus sessionStatus;
    private ApplicationDetails applicationDetails = new ApplicationDetails(new ArrayList<>());
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Session(SessionPeriod period, SessionImage image, SessionType sessionType) {
        this(0L, image, period, sessionType, new SessionStatus(), LocalDateTime.now(), null);
    }

    public Session(SessionPeriod period, SessionImage image, SessionType sessionType, SessionStatus sessionStatus) {
        this(0L, image, period, sessionType, sessionStatus, LocalDateTime.now(), null);
    }

    public Session(Long sessionId, SessionPeriod sessionPeriod,
                   SessionStatus sessionStatus,
                   SessionType sessionType,
                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        this(sessionId, null, sessionPeriod, sessionType, sessionStatus, createdAt, updatedAt);
    }

    public Session(Long sessionId, SessionImage sessionImage, SessionPeriod sessionPeriod, SessionType sessionType, SessionStatus sessionStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = sessionId;
        this.sessionImage = sessionImage;
        this.sessionPeriod = sessionPeriod;
        this.sessionType = sessionType;
        this.sessionStatus = sessionStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void apply(NsUser nsUser, Optional<Payment> payment) {
        sessionStatus.isValidStatusForApplicationNew();

        sessionType.checkMaxNumber(applicationDetails.size());
        if (payment.isPresent()) {
            sessionType.validatePaymentAmount(payment.get());
        }
        applicationDetails.canApply(nsUser.getId(), this.id);

        applicationDetails.add(new ApplicationDetail(this.id, nsUser.getId()));
    }

    public void startRecruiting() {
        SessionStatus updatedSessionStatus = sessionStatus.startRecruiting();
        this.sessionStatus = updatedSessionStatus;
    }

    public boolean isRecruiting() {
        return sessionStatus.isRecruiting();
    }

    public void modifyPeriod(SessionPeriod period) {
        if (this.sessionPeriod.equals(period)) {
            throw new IllegalArgumentException("변경 하려는 기간과 기존 기간이 동일합니다.");
        }
        this.sessionPeriod = period;
    }

    public String getStartDate() {
        return this.sessionPeriod.getStartDate();
    }

    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }

    public String getEndDate() {
        return this.sessionPeriod.getEndDate();
    }

    public String getStatusCode() {
        return this.sessionStatus.getStatus();
    }

    public Long getId() {
        return this.id;
    }

    public String getTypeCode() {
        return this.sessionType.getTypeCode();
    }

    public Long getFeeAmount() {
        return this.sessionType.getFeeAmount();
    }

    public int getMaxNumberOfApplicants() {
        return this.sessionType.getMaxNumberOfUser();
    }

    public void addApplicationDetails(List<ApplicationDetail> applicationDetailList) {
        this.applicationDetails.add(applicationDetailList);
    }

    public ApplicationDetail getApplicationDetail(Long userId, long sessionId) {
        return applicationDetails.getMatch(sessionId, userId);
    }

    public boolean isFree() {
        return sessionType.isFree();
    }

    public Long getCourseId() {
        return course.getId();
    }

    public void toCourse(Course course) {
        this.course = course;
    }
}
