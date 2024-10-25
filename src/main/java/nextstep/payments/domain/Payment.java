package nextstep.payments.domain;

import nextstep.courses.domain.PaidSession;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class Payment {
    private String id;

    // 결제한 강의 아이디
    private Long sessionId;

    // 결제한 사용자 아이디
    private Long nsUserId;

    // 결제 금액
    private Long amount;

    private LocalDateTime createdAt;

    public Payment() {
    }

    public Payment(String id, Long sessionId, Long nsUserId, Long amount) {
        this.id = id;
        this.sessionId = sessionId;
        this.nsUserId = nsUserId;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }


    public boolean isPaidUser(PaidSession session, NsUser user) {
        return this.sessionId.equals(session.getSessionId()) && this.nsUserId.equals(user.getId());
    }
}
