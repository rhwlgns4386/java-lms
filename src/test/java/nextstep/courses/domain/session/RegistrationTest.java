package nextstep.courses.domain.session;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RegistrationTest {
    public static final Registration REGISTRATION = new Registration(1L, NsUserTest.JAVAJIGI, new Payment("1", 1L, NsUserTest.JAVAJIGI.getId(), 200_000L));
    public static final Registration REGISTRATION2 = new Registration(1L, NsUserTest.SANJIGI, new Payment("1", 1L, NsUserTest.SANJIGI.getId(), 200_000L));

    @Test
    @DisplayName("Registration 생성")
    void createRegistrationTest() {
        Payment payment = new Payment("1", 1L, NsUserTest.JAVAJIGI.getId(), 200_000L);

        Registration registration = new Registration(1L, NsUserTest.JAVAJIGI, payment);

        Assertions.assertThat(registration).isNotNull();
    }

    @Test
    @DisplayName("NsUser null 체크")
    void checkNsUserNullTest() {
        Payment payment = new Payment("1", 1L, NsUserTest.JAVAJIGI.getId(), 200_000L);

        Assertions.assertThatThrownBy(() -> new Registration(1L, null, payment))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Payment null 체크")
    void checkPaymentNullTest() {
        Assertions.assertThatThrownBy(() -> new Registration(1L, NsUserTest.JAVAJIGI, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Payment user_id와 NeUser가 다른 경우 체크")
    void checkPaymentUserIdAndNsUserNotMantchTest() {
        Payment payment = new Payment("1", 1L, NsUserTest.JAVAJIGI.getId(), 200_000L);
        Assertions.assertThatThrownBy(() -> new Registration(1L, NsUserTest.SANJIGI, payment))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
