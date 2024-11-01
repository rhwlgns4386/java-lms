package nextstep.payments.domain;

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

    public void validateSessionFee(int sessionFee) {
        if (this.amount != sessionFee) {
            throw new IllegalArgumentException("고객이 결제한 금액과 수강료가 일치하지 않습니다.");
        }
    }

    public Long getNsUserId() {
        return nsUserId;
    }
}
