package nextstep.courses.domain.session;

import nextstep.courses.domain.enroll.EnrollUserInfo;
import nextstep.courses.domain.enroll.EnrollUserInfos;
import nextstep.courses.domain.image.SessionCoverImage;
import nextstep.courses.domain.strategy.PaymentStrategy;
import nextstep.payments.domain.Payment;
import nextstep.payments.domain.Payments;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class Session {
    private static final String SESSION_STATUS_ERROR = "이 강의는 현재 모집중이 아닙니다.";

    private final Long sessionId;
    private final EnrollUserInfos enrollUserInfos;
    private final SessionPrice sessionPrice;
    private final SessionStatus sessionStatus;
    private final SessionCoverImage sessionCoverImage;
    private final SessionDate sessionDate;
    private final Payments payments;

    private Session(Long sessionId, EnrollUserInfos enrollUserInfos, SessionPrice sessionPrice, SessionStatus sessionStatus, SessionCoverImage sessionCoverImage, SessionDate sessionDate, Payments payments) {
        this.sessionId = sessionId;
        this.enrollUserInfos = enrollUserInfos;
        this.sessionPrice = sessionPrice;
        this.sessionStatus = sessionStatus;
        this.sessionCoverImage = sessionCoverImage;
        this.sessionDate = sessionDate;
        this.payments = payments;
    }

    public static Session createSessionOf(Long sessionId, Long price, SessionPriceType sessionPriceType, SessionStatus sessionStatus, SessionCoverImage sessionCoverImage, LocalDateTime startDateTime, LocalDateTime endDateTime, int availableEnrollCount) {
        SessionPrice newSessionPrice = SessionPrice.createSessionPriceOf(sessionPriceType, price);
        SessionDate newSessionDate = new SessionDate(startDateTime, endDateTime);
        EnrollUserInfos newEnrollUserInfos = new EnrollUserInfos(availableEnrollCount);
        Payments newPayments = new Payments();
        return new Session(sessionId, newEnrollUserInfos, newSessionPrice, sessionStatus, sessionCoverImage, newSessionDate, newPayments);
    }

    private static Session createSessionOf(SessionBuilder sessionBuilder) {
        SessionPrice newSessionPrice = SessionPrice.createSessionPriceOf(sessionBuilder.sessionPriceType, sessionBuilder.price);
        SessionDate newSessionDate = new SessionDate(sessionBuilder.startDateTime, sessionBuilder.endDateTime);
        EnrollUserInfos newEnrollUserInfos = new EnrollUserInfos(sessionBuilder.availableEnrollCount);
        Payments newPayments = new Payments();
        return new Session(sessionBuilder.sessionId, newEnrollUserInfos, newSessionPrice, sessionBuilder.sessionStatus, sessionBuilder.sessionCoverImage, newSessionDate, newPayments);
    }

    public void enroll(NsUser nsUser, Long price, PaymentStrategy paymentStrategy) {
        checkSessionStatus();
        sessionPrice.checkPaymentPrice(price);
        enrollUserInfos.checkPaidSessionEnroll(sessionPrice.getSessionPriceType());
        EnrollUserInfo enrollUserInfo = new EnrollUserInfo(sessionId, nsUser.getId());
        enrollUserInfos.add(enrollUserInfo);
        payments.addPayments(new Payment(paymentStrategy.generate(), sessionId, nsUser.getId(), price));
    }

    private void checkSessionStatus() {
        if (sessionStatus != SessionStatus.ENROLLING) {
            throw new IllegalArgumentException(SESSION_STATUS_ERROR);
        }
    }

    public Set<EnrollUserInfo> getEnrollUserInfos() {
        return this.enrollUserInfos.getEnrollUserInfos();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Session session = (Session) o;
        return Objects.equals(sessionId, session.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId);
    }

    public static class SessionBuilder {
        private Long sessionId;
        private Long price;
        private SessionPriceType sessionPriceType;
        private SessionStatus sessionStatus;
        private SessionCoverImage sessionCoverImage;
        private LocalDateTime startDateTime;
        private LocalDateTime endDateTime;
        private int availableEnrollCount;

        public SessionBuilder(Long sessionId) {
            this.sessionId = sessionId;
        }

        public SessionBuilder price(Long price) {
            this.price = price;
            return this;
        }

        public SessionBuilder sessionPriceType(SessionPriceType sessionPriceType) {
            this.sessionPriceType = sessionPriceType;
            return this;
        }

        public SessionBuilder sessionStatus(SessionStatus sessionStatus) {
            this.sessionStatus = sessionStatus;
            return this;
        }

        public SessionBuilder sessionCoverImage(SessionCoverImage sessionCoverImage) {
            this.sessionCoverImage = sessionCoverImage;
            return this;
        }

        public SessionBuilder startDateTime(LocalDateTime startDateTime) {
            this.startDateTime = startDateTime;
            return this;
        }

        public SessionBuilder endDateTime(LocalDateTime endDateTime) {
            this.endDateTime = endDateTime;
            return this;
        }

        public SessionBuilder availableEnrollCount(int availableEnrollCount) {
            this.availableEnrollCount = availableEnrollCount;
            return this;
        }

        public Session build() {
            return Session.createSessionOf(this);
        }

    }

}
