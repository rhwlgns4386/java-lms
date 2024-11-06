package nextstep.courses.domain;

import nextstep.courses.exception.CannotApproveSessionException;

public class Instructor {
    private final long id;

    private final String instructorId;

    public Instructor(final long id) {
        this(id, null);
    }

    public Instructor(final long id, String instructorId) {
        this.id = id;
        this.instructorId = instructorId;
    }

    public SessionOrder approveSessionOrder(SessionOrder sessionOrder) throws CannotApproveSessionException {
        if (!sessionOrder.isReadyOrderState()) {
            throw new CannotApproveSessionException("대기중인 신청상태만 승인 가능합니다.");
        }
        return sessionOrder.createApproveSessionOrder(this);
    }

    public SessionOrder cancelSessionOrder(SessionOrder sessionOrder) throws CannotApproveSessionException {
        if (!sessionOrder.isReadyOrderState()) {
            throw new CannotApproveSessionException("대기중인 신청상태만 승인 취소 가능합니다.");
        }
        return sessionOrder.createCancelSessionOrder(this);
    }

    public long getId() {
        return id;
    }
}
