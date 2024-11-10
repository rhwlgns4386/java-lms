package nextstep.courses.domain;

import nextstep.courses.exception.CannotApproveSessionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class InstructorTest {

    @Test
    @DisplayName("주문상태가 대기상태의 주문신청을 강사는 승인")
    void approveSessionOrder() throws CannotApproveSessionException {
        Instructor instructor = new Instructor(new InstructorId(7));
        instructor.approveSessionOrder(SessionOrderTest.SESSION_ORDER_READY);
    }

    @Test
    @DisplayName("주문상태가 대기상태가 아니면 강사는 승인 시 승인불가 오류")
    void approveSessionOrder_CannotApproveSessionException()  {
        Instructor instructor = new Instructor(new InstructorId(7));

        assertThatThrownBy(() -> instructor.approveSessionOrder(SessionOrderTest.SESSION_ORDER_APPROVE)).isInstanceOf(CannotApproveSessionException.class);
        assertThatThrownBy(() -> instructor.approveSessionOrder(SessionOrderTest.SESSION_ORDER_CANCEL)).isInstanceOf(CannotApproveSessionException.class);
    }

    @Test
    @DisplayName("주문상태가 대기상태의 주문신청을 강사는 거절")
    void cancelSessionOrder() throws CannotApproveSessionException {
        Instructor instructor = new Instructor(new InstructorId(7));
        instructor.approveSessionOrder(SessionOrderTest.SESSION_ORDER_READY);
    }

    @Test
    @DisplayName("주문상태가 대기상태가 아니면 강사는 거절 시 승인불가 오류")
    void cancelSessionOrder_CannotApproveSessionException()  {
        Instructor instructor = new Instructor(new InstructorId(7));

        assertThatThrownBy(() -> instructor.cancelSessionOrder(SessionOrderTest.SESSION_ORDER_APPROVE)).isInstanceOf(CannotApproveSessionException.class);
        assertThatThrownBy(() -> instructor.cancelSessionOrder(SessionOrderTest.SESSION_ORDER_CANCEL)).isInstanceOf(CannotApproveSessionException.class);
    }


}
