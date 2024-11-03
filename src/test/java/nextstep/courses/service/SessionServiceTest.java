package nextstep.courses.service;

import nextstep.courses.exception.CannotRegisteSessionException;
import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.Session;
import nextstep.courses.factory.SessionFactory;
import nextstep.courses.domain.SessionImage;
import nextstep.courses.domain.SessionInfo;
import nextstep.courses.domain.SessionType;
import nextstep.courses.collection.Sessions;
import nextstep.courses.domain.StateCode;
import nextstep.courses.infrastructure.SessionRepository;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {
    @InjectMocks
    private SessionService sessionService;

    @Mock
    private SessionRepository sessionRepository;

    private Payment payment = new Payment("id", 1L, 1L, 1000L);
    private SessionInfo sessionInfo;
    private SessionImage sessionImage;

    @BeforeEach
    void setUp() {
        sessionInfo = new SessionInfo("제목1", LocalDateTime.now(), LocalDateTime.now().plus(10, ChronoUnit.HALF_DAYS), "createorId");
        sessionImage = new SessionImage(100, "jpg", 300, 200, "imageFileName1");
    }

    @Test
    @DisplayName("유료 강의를 등록한다")
    void registerPaidSession() {
        Session session = SessionFactory.createSession(sessionInfo, sessionImage, 1000, StateCode.RECRUITING, 2, SessionType.PAID);

        when(sessionRepository.insert(any(Session.class))).thenReturn(session);

        sessionService.registerPaidSession(sessionInfo, sessionImage, 1000, StateCode.RECRUITING, 2, SessionType.PAID);

        verify(sessionRepository).insert(any(PaidSession.class));
    }

    @Test
    @DisplayName("무료 강의를 등록한다")
    void registeFreeSession() {
        Session session = SessionFactory.createSession(sessionInfo, sessionImage, 0, StateCode.RECRUITING, SessionType.FREE);

        when(sessionRepository.insert(any(Session.class))).thenReturn(session);

        sessionService.registerPaidSession(sessionInfo, sessionImage, 0, StateCode.RECRUITING, 2, SessionType.FREE);

        verify(sessionRepository).insert(any(FreeSession.class));
    }

    @Test
    @DisplayName("강의 신청 테스트")
    void orderPaidSession() throws CannotRegisteSessionException {
        Session paidSession = SessionFactory.createSession(sessionInfo, sessionImage, 1000, StateCode.RECRUITING, 2, SessionType.PAID);
        Session freeSession = SessionFactory.createSession(sessionInfo, sessionImage, 0, StateCode.RECRUITING, SessionType.FREE);
        List<Session> sessionList = new ArrayList<>();
        sessionList.add(paidSession);
        sessionList.add(freeSession);
        Sessions sessions = new Sessions(sessionList);

        sessionService.orderPaidSession(sessions, payment, NsUserTest.SANJIGI, 0);
        sessionService.orderPaidSession(sessions, payment, NsUserTest.JAVAJIGI, 0);

        assertThat(sessions.getSessionIdx(0).getStudentsSize()).isEqualTo(2);
    }
}
