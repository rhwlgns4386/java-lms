package nextstep.courses.domain;

public class SessionAddInfo {
    private final Long nsUserId;
    private final Long amount;

    public SessionAddInfo(Long nsUserId, Long amount) {
        this.nsUserId = nsUserId;
        this.amount = amount;
    }

    public Long getNsUserId() {
        return nsUserId;
    }

    public Long getAmount() {
        return amount;
    }
}
