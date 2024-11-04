package nextstep.payments.domain;

import java.time.LocalDateTime;

public class Payment {
    private final static String INVALID_PAYMENT_INFO = "결제 정보가 세션 및 수강생 정보와 일치하지 않습니다.";
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

    public Payment(Long sessionId, Long nsUserId, Long amount) {
        this(0L, sessionId, nsUserId, amount);
    }

    public Payment(Long id, Long sessionId, Long nsUserId, Long amount) {
        this.id = id;
        this.sessionId = sessionId;
        this.nsUserId = nsUserId;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }

    public void validateForSessionAndUser(Long sessionId, Long userId, Long fee) {
        if (!this.sessionId.equals(sessionId) ||
                !this.nsUserId.equals(userId) ||
                !this.amount.equals(fee)) {
            throw new IllegalArgumentException(INVALID_PAYMENT_INFO);
        }
    }

    public Long getAmount() {
        return amount;
    }

    public long getSessionId() {
        return sessionId;
    }
}
