package nextstep.users.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import nextstep.payments.domain.Payment;

public class NsUserTest {
    public static final NsUser JAVAJIGI = new NsUser(1L, "javajigi", "password", "name", "javajigi@slipp.net");
    public static final NsUser SANJIGI = new NsUser(2L, "sanjigi", "password", "name", "sanjigi@slipp.net");
    public static final NsUser GYEONGJAE = new NsUser(3L, "gyeongjae", "password", "name", "gyeongjae@slipp.net");

    @Test
    void 결제정보의_id를_검증한다() {
        Payment payment = new Payment("id", 1L, 1L, 3000L);
        boolean result = JAVAJIGI.verifyId(payment);

        assertThat(result).isTrue();
    }
}
