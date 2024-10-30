package nextstep.courses.domain.session;

import nextstep.courses.domain.enroll.EnrollUserInfo;
import nextstep.courses.domain.enroll.EnrollUserInfos;
import nextstep.courses.domain.image.SessionCoverImage;
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

    private Session(Long sessionId, EnrollUserInfos enrollUserInfos, SessionPrice sessionPrice, SessionStatus sessionStatus, SessionCoverImage sessionCoverImage, SessionDate sessionDate) {
        this.sessionId = sessionId;
        this.enrollUserInfos = enrollUserInfos;
        this.sessionPrice = sessionPrice;
        this.sessionStatus = sessionStatus;
        this.sessionCoverImage = sessionCoverImage;
        this.sessionDate = sessionDate;
    }

    public static Session createSessionOf(Long sessionId, Long price, SessionPriceType sessionPriceType, SessionStatus sessionStatus, SessionCoverImage sessionCoverImage, LocalDateTime startDateTime, LocalDateTime endDateTime, int availableEnrollCount) {
        SessionPrice newSessionPrice = SessionPrice.createSessionPriceOf(sessionPriceType, price);
        SessionDate newSessionDate = new SessionDate(startDateTime, endDateTime);
        EnrollUserInfos newEnrollUserInfos = new EnrollUserInfos(availableEnrollCount);

        return new Session(sessionId, newEnrollUserInfos , newSessionPrice, sessionStatus, sessionCoverImage, newSessionDate);
    }


    public void enroll(NsUser nsUser, Long price) {
        checkSessionStatus();
        sessionPrice.checkPaymentPrice(price);
        enrollUserInfos.checkPaidSessionEnroll(sessionPrice.getSessionPriceType());
        EnrollUserInfo enrollUserInfo = new EnrollUserInfo(sessionId, nsUser.getId());
        enrollUserInfos.add(enrollUserInfo);
    }

    private void checkSessionStatus() {
        if(sessionStatus != SessionStatus.ENROLLING) {
            throw new IllegalArgumentException(SESSION_STATUS_ERROR);
        }
    }

    public Set<EnrollUserInfo> getEnrollUserInfos() {
        return this.enrollUserInfos.getEnrollUserInfos();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(sessionId, session.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId);
    }
}
