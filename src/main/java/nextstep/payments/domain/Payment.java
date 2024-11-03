package nextstep.payments.domain;

import java.time.LocalDateTime;

import nextstep.courses.utils.UUIDGenerator;

public class Payment {
    private String id;

    // 결제한 강의 아이디
    private String sessionId;

    // 결제한 사용자 아이디
    private Long nsUserId;

    // 결제 금액
    private Long amount;

    private LocalDateTime createdAt;

    public Payment() {
    }

    public Payment(String sessionId, Long nsUserId, Long amount) {
        this.id = UUIDGenerator.getUUID();
        this.sessionId = sessionId;
        this.nsUserId = nsUserId;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }

    public Long getAmount() {
        return amount;
    }
}
