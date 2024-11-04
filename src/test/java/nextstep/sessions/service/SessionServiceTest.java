package nextstep.sessions.service;

import nextstep.payments.domain.Payment;
import nextstep.payments.service.PaymentService;
import nextstep.sessions.domain.*;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private PaymentService paymentService;

    @Mock
    private ApplicationDetailRepository applicationDetailRepository;

    @InjectMocks
    private SessionService sessionService;

    private Session recruiting_session;
    private Session preparing_session;
    private Session recruiting_paid_session;

    @BeforeEach
    public void setUp() throws Exception {
        recruiting_session = new Session(1L, null, new SessionPeriod("20250101", "20250301"), new SessionType(), new SessionStatus(SessionProgressStatusEnum.PROGRESSING, SessionRecruitmentStatusEnum.RECRUITING), LocalDateTime.now(), null);
        recruiting_paid_session = new Session(3L, null, new SessionPeriod("20250101", "20250301"), new SessionType(Long.valueOf(200000), 1), new SessionStatus(SessionProgressStatusEnum.PROGRESSING, SessionRecruitmentStatusEnum.RECRUITING), LocalDateTime.now(), null);
        preparing_session = new Session(2L, null, new SessionPeriod("20250101", "20250301"), new SessionType(), new SessionStatus(SessionProgressStatusEnum.PROGRESSING, SessionRecruitmentStatusEnum.NON_RECRUITING), LocalDateTime.now(), null);
    }


    @Test
    @DisplayName("수강 신청")
    void apply_normal() throws Exception {
        when(sessionRepository.findById(recruiting_session.getId())).thenReturn(Optional.of(recruiting_session));

        assertThat(recruiting_session.isRecruiting()).isTrue();

        sessionService.apply(NsUserTest.JAVAJIGI, 1L);

        assertThat(recruiting_session.getApplicationDetail(NsUserTest.JAVAJIGI.getId(), 1L).isPresent(NsUserTest.JAVAJIGI.getId(), 1L)).isTrue();
    }

    @Test
    @DisplayName("유료 강의 수강 신청_결제금액오류")
    void apply_validation_paymentAmount() throws Exception {

        when(sessionRepository.findById(recruiting_paid_session.getId())).thenReturn(Optional.of(recruiting_paid_session));
        when(paymentService.payment("201")).thenReturn(new Payment("201", 3L, NsUserTest.JAVAJIGI.getId(), Long.valueOf(190000)));

        assertThatThrownBy(() -> sessionService.apply(NsUserTest.JAVAJIGI, 3L)).isInstanceOf(RuntimeException.class).hasMessage("결제금액 오류 입니다.");
    }

    @Test
    @DisplayName("유료 강의 수강 신청_수강인원오류")
    void apply_validation_maximumNumber() throws Exception {

        when(sessionRepository.findById(recruiting_paid_session.getId())).thenReturn(Optional.of(recruiting_paid_session));
        when(paymentService.payment("201")).thenReturn(new Payment("201", 3L, NsUserTest.JAVAJIGI.getId(), Long.valueOf(200000)));

        sessionService.apply(NsUserTest.JAVAJIGI, 3L);

        assertThatThrownBy(() -> sessionService.apply(NsUserTest.SANJIGI, 3L)).isInstanceOf(RuntimeException.class).hasMessage("수강 가능 최대 인원을 초과하였습니다");
    }

    @Test
    @DisplayName("수강 신청 실패_준비중강의")
    void apply_fail() throws Exception {

        when(sessionRepository.findById(preparing_session.getId())).thenReturn(Optional.of(preparing_session));

        assertThat(preparing_session.isRecruiting()).isFalse();

        assertThatThrownBy(() -> sessionService.apply(NsUserTest.JAVAJIGI, 1L)).isInstanceOf(RuntimeException.class);
    }

}
