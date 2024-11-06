package nextstep.payments.domain;

import java.time.LocalDateTime;

public class Payment {
    private Long id;

    // 결제한 강의 아이디
    private Long sessionId;

    // 결제한 사용자 아이디
    private Long nsUserId;

    // 결제 금액
    private Long amount;

    private LocalDateTime createdAt;

    public Payment() {
    }

    public Payment(Long id, Long sessionId, Long nsUserId, Long amount, LocalDateTime createdAt) {
        this.id = id;
        this.sessionId = sessionId;
        this.nsUserId = nsUserId;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public Long getAmount() {
        return amount;
    }

    public Long getId() {
        return id;
    }
}
