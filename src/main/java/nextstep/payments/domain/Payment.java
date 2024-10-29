package nextstep.payments.domain;

import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class Payment {
    private String id;

    // 결제한 강의 아이디
    private Long sessionId;

    // 결제한 사용자 아이디
    private NsUser nsUser;

    // 결제 금액
    private Long amount;

    private LocalDateTime createdAt;

    public Payment() {
    }

    public Payment(String id, Long sessionId, NsUser nsUser, Long amount) {
        this.id = id;
        this.sessionId = sessionId;
        this.nsUser = nsUser;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }

    public NsUser payingUser() {
        return nsUser;
    }

    public boolean matchingAmount(int sessionAmount) {
        return amount == sessionAmount;
    }
}
