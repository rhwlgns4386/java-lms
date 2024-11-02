package nextstep.courses.service;

import nextstep.courses.CannotRegisteSessionException;
import nextstep.courses.domain.Sessions;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {
    @InjectMocks
    private SessionService sessionService;
    private Payment payment = new Payment("id", 1L, 1L, 1000L);

    @Test
    @DisplayName("강의 신청 테스트")
    void orderPaidSession() throws CannotRegisteSessionException {
        Sessions sessions = sessionService.registerPaidSession();
        sessionService.orderPaidSession(sessions, payment, NsUserTest.SANJIGI,0);
        sessionService.orderPaidSession(sessions, payment, NsUserTest.JAVAJIGI, 0);
        assertThat(sessions.getSessionIdx(0).getStudentsSize()).isEqualTo(2);
    }
}
