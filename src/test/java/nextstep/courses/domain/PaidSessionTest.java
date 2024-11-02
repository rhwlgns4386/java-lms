package nextstep.courses.domain;

import nextstep.courses.CannotRegisteSessionException;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PaidSessionTest {
    public final static PaidSession PAID_SESSION = new PaidSession("강의명", LocalDateTime.now(), LocalDateTime.now().plus(20, ChronoUnit.HALF_DAYS),
            1000L, StateCode.RECRUITING, 1, 200, "png", 600, 400, "imageFileName", 100);


    private PaidSession paidSession;
    private PaidSession maxStudentTwoSession;
    private Payment payment = new Payment("id", 1L, 1L, 1000L);

    @BeforeEach
    void setUp() {
        paidSession = new PaidSession("1명모집", LocalDateTime.now(), LocalDateTime.now().plus(20, ChronoUnit.HALF_DAYS),
                1000L, StateCode.RECRUITING, 2, 200, "png", 600, 400, "imageFileName", 1);
        maxStudentTwoSession = new PaidSession("2명모집", LocalDateTime.now(), LocalDateTime.now().plus(20, ChronoUnit.HALF_DAYS),
                1000L, StateCode.RECRUITING, 3, 200, "png", 600, 400, "imageFileName", 2);
    }

    @Test
    @DisplayName("결제한 금액과 수강료가 일치 / 상태가 모집중일 경우 pass")
    void validateRegisterSession() throws CannotRegisteSessionException {
        paidSession.validateOrderSession(new RequestOrderParam(payment));
    }

    @Test
    @DisplayName("상태가 모집중이 아닌경우 오류")
    void validateStateCode_IllegalArgumentException() {
        PaidSession paidSession = new PaidSession("제목2", LocalDateTime.now(), LocalDateTime.now().plus(20, ChronoUnit.HALF_DAYS),
                1000L, StateCode.READY, 4, 200, "png", 600, 400, "imageFileName2", 1);

        assertThatThrownBy(() -> {
            paidSession.validateOrderSession(new RequestOrderParam(payment));
        }).isInstanceOf(IllegalArgumentException.class).hasMessageStartingWith("모집하지 않는 강의입니다.");

    }

    @Test
    @DisplayName("결제한 금액과 수강료가 일치하지 않을 경우")
    void validateRegisterSession_CannotRegisteSessionException() {
        Payment paymentFail = new Payment("id", 1L, 1L, 2000L);

        assertThatThrownBy(() -> {
            paidSession.validateOrderSession(new RequestOrderParam(paymentFail));
        }).isInstanceOf(CannotRegisteSessionException.class);
    }

    @Test
    @DisplayName("최대 수강 인원 이하 pass")
    void validateMaxStudendtCount() throws CannotRegisteSessionException {
        paidSession.orderSession(new RequestOrderParam(payment, NsUser.GUEST_USER));
    }

    @Test
    @DisplayName("최대 수강 인원 초과 오류")
    void validateMaxStudentCount_CannotRegisteSessionException() throws CannotRegisteSessionException {
        paidSession.validateOrderSession(new RequestOrderParam(payment));
        paidSession.orderSession(new RequestOrderParam(payment, NsUser.GUEST_USER));

        assertThatThrownBy(() -> {
            paidSession.validateOrderSession(new RequestOrderParam(payment, NsUser.GUEST_USER));
        }).isInstanceOf(CannotRegisteSessionException.class).hasMessageStartingWith("최대인원 수를 초과하였습니다.");
    }

    @Test
    @DisplayName("강의 주문시 학생이 추가됨")
    void orderSession() throws CannotRegisteSessionException {
        maxStudentTwoSession.orderSession(new RequestOrderParam(payment, NsUserTest.SANJIGI));//1명짜리

        assertThat(maxStudentTwoSession.getStudentSize()).isEqualTo(1);

        maxStudentTwoSession.orderSession(new RequestOrderParam(payment, NsUserTest.JAVAJIGI));//1명짜리

        assertThat(maxStudentTwoSession.getStudentSize()).isEqualTo(2);
    }

    @Test
    @DisplayName("강의 중복 신청 오류")
    void validateDuplicate() throws CannotRegisteSessionException {
        maxStudentTwoSession.orderSession(new RequestOrderParam(payment, NsUserTest.SANJIGI));//1명짜리
        assertThatThrownBy(() -> {
            maxStudentTwoSession.orderSession(new RequestOrderParam(payment, NsUserTest.SANJIGI));//1명짜리
        }).isInstanceOf(CannotRegisteSessionException.class).hasMessageStartingWith("강의는 중복 신청할 수 없습니다.");
    }

    @Test
    @DisplayName("강의 주문시 최대 수강 인원 초과 오류")
    void orderSession_CannotRegisteSessionException() throws CannotRegisteSessionException {
        paidSession.orderSession(new RequestOrderParam(payment, NsUser.GUEST_USER));//1명짜리

        assertThatThrownBy(() -> {
            paidSession.orderSession(new RequestOrderParam(payment, NsUser.GUEST_USER));//1명짜리
        }).isInstanceOf(CannotRegisteSessionException.class).hasMessageStartingWith("최대인원 수를 초과하였습니다.");
    }


    @Test
    @DisplayName("수강생이 결제한 금액과 수강료가 일치한 경우 pass")
    void validSessionRegister_NoMatchException() {
        Payment payment = new Payment("id", 1L, 1L, 2000L);
        assertThatThrownBy(() -> {
            paidSession.validateOrderSession(new RequestOrderParam(payment));
        }).isInstanceOf(CannotRegisteSessionException.class).hasMessageStartingWith("결제한 금액과 수강료가 일치하지 않습니다.");
    }


}
