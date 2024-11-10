package nextstep.courses.domain;


import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionOrderTest {
    public final static SessionOrder SESSION_ORDER_APPROVE = new SessionOrder(1,1,new NsUser(1L), OrderStateCode.APPROVE, new SessionPrice(1000), new Instructor(new InstructorId(7)));
    public final static SessionOrder SESSION_ORDER_READY = new SessionOrder(1,1,new NsUser(1L), OrderStateCode.READY, new SessionPrice(1000), new Instructor(new InstructorId(7)));
    public final static SessionOrder SESSION_ORDER_CANCEL = new SessionOrder(1,1,new NsUser(1L), OrderStateCode.CANCEL, new SessionPrice(1000), new Instructor(new InstructorId(7)));

    @Test
    @DisplayName("강의신청주문의 주문상태 여부를 조회")
    void isReadyOrderState() {
        assertThat(SESSION_ORDER_APPROVE.isReadyOrderState()).isFalse();
        assertThat(SESSION_ORDER_READY.isReadyOrderState()).isTrue();
        assertThat(SESSION_ORDER_CANCEL.isReadyOrderState()).isFalse();
    }

    @Test
    @DisplayName("강의신청주문을 승인하면 승인상태의 새로운 강의신청주문을 반환")
    void createApproveSessionOrder() {
        assertThat(SESSION_ORDER_READY.createApproveSessionOrder(new Instructor(new InstructorId(7))).getOrderStateCode()).isEqualTo(OrderStateCode.APPROVE.getOrderStateCode());
    }

    @Test
    @DisplayName("강의신청주문을 거절하면 승인상태의 새로운 강의신청주문을 반환")
    void createCancelSessionOrder() {
        assertThat(SESSION_ORDER_READY.createCancelSessionOrder(new Instructor(new InstructorId(7))).getOrderStateCode()).isEqualTo(OrderStateCode.CANCEL.getOrderStateCode());
    }


}
