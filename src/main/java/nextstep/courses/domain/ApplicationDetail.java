package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

import java.time.LocalDateTime;

public class ApplicationDetail {
    private Long sessionId ;
    private Long nsUserId;
    private String paymentId;
    private LocalDateTime createdAt;

    public ApplicationDetail(Long sessionId, Long nsUserId, String paymentId) {
        if (sessionId == null || nsUserId == null) {
            throw new RuntimeException("수강신청내역에 강의식별자/고객식별자는 필수 입력입니다");
        }
        this.sessionId = sessionId;
        this.nsUserId = nsUserId;
        this.paymentId = paymentId;
        this.createdAt = LocalDateTime.now();
    }

    public static ApplicationDetail ofNewFreeTypeInstance(Long sessionId, Long nsUserId) {
        return new ApplicationDetail(sessionId, nsUserId, null);
    }

    public static ApplicationDetail ofNewPaidTypeInstance(Long sessionId, Long nsUserId, String paymentId) {
        return new ApplicationDetail(sessionId, nsUserId, paymentId);
    }


    public Long getSessionId() {
        return sessionId;
    }

    public Long getNsUserId() {
        return nsUserId;

    }

    public String getPaymentId() {
        return paymentId;
    }
}
