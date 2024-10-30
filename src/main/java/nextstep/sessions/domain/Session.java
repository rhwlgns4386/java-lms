package nextstep.sessions.domain;

import nextstep.courses.domain.Course;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class Session {
    private Long id;
    private Course course;
    private SessionImage sessionImage;
    private SessionPeriod sessionPeriod;
    private SessionType sessionType;
    private SessionStatus sessionStatus;
    private ApplicationDetails applicationDetails;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Session(SessionPeriod period, SessionImage image, SessionType sessionType) {
        this(null, image, period, sessionType, new SessionStatus(SessionStatusEnum.PREPARING.getValue()));
    }

    public Session(Long sessionId,SessionImage sessionImage,  SessionPeriod sessionPeriod, SessionType sessionType, SessionStatus sessionStatus) {
        this.id = sessionId;
        this.sessionImage = sessionImage;
        this.sessionPeriod = sessionPeriod;
        this.sessionType = sessionType;
        this.sessionStatus = sessionStatus;
    }

    public void apply(NsUser nsUser, Payment payment) {
        sessionStatus.isValidStatusForApplication();

        sessionType.checkMaxNumber(applicationDetails.size());
        sessionType.validatePaymentAmount(payment.getAmount());

        applicationDetails.canApply(nsUser.getId(), this.id);

        ApplicationDetail applicationDetail = new ApplicationDetail(this.id, nsUser.getId(), payment.getId());
        applicationDetails.add(applicationDetail);
    }
}
