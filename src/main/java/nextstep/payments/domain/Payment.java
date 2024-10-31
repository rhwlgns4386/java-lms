package nextstep.payments.domain;

import java.time.LocalDateTime;
import java.util.Objects;

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

    public Long getAmount() {
        return amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Payment payment = (Payment) o;
        return Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
