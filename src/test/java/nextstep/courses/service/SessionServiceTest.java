package nextstep.courses.service;

import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.Instructor;
import nextstep.courses.domain.InstructorId;
import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.ProgressCode;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionFactory;
import nextstep.courses.domain.SessionImages;
import nextstep.courses.domain.SessionImagesTest;
import nextstep.courses.domain.SessionInfo;
import nextstep.courses.domain.SessionMetaData;
import nextstep.courses.domain.SessionOrder;
import nextstep.courses.domain.SessionOrderTest;
import nextstep.courses.domain.SessionPeriod;
import nextstep.courses.domain.SessionPrice;
import nextstep.courses.domain.SessionType;
import nextstep.courses.domain.StateCode;
import nextstep.courses.domain.Students;
import nextstep.courses.exception.CannotApproveSessionException;
import nextstep.courses.exception.CannotRegisteSessionException;
import nextstep.courses.infrastructure.SessionOrderRepository;
import nextstep.courses.infrastructure.SessionRepository;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {
    @InjectMocks
    private SessionService sessionService;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private SessionOrderRepository sessionOrderRepository;

    private Payment payment = new Payment("id", 1L, 1L, 1000L);
    private SessionInfo sessionInfo;
    private SessionInfo sessionInfoReady;

    @BeforeEach
    void setUp() {
        sessionInfo = new SessionInfo(new SessionMetaData("제목", "작성자"),
                new SessionPeriod(LocalDateTime.now(), LocalDateTime.now().plus(10, ChronoUnit.HALF_DAYS)),
                StateCode.RECRUITING, ProgressCode.PROGRESS, new Instructor(new InstructorId(1)));
        sessionInfoReady = new SessionInfo(new SessionMetaData("제목", "작성자"),
                new SessionPeriod(LocalDateTime.now(), LocalDateTime.now().plus(10, ChronoUnit.HALF_DAYS)),
                StateCode.RECRUITING, ProgressCode.PROGRESS, new Instructor(new InstructorId(2)));
    }

    @Test
    @DisplayName("유료 강의를 등록한다")
    void registerPaidSession() {
        Session session = SessionFactory.createSession(sessionInfo, SessionImagesTest.SESSION_IMAGE, new SessionPrice(1000L), 2, SessionType.PAID);

        //  when(sessionRepository.save(any(Session.class))).thenReturn(1);

        sessionService.registerPaidSession(sessionInfo, SessionImagesTest.SESSION_IMAGE, new SessionPrice(1000L), 2, SessionType.PAID);

        verify(sessionRepository).saveRegisterSession(any(PaidSession.class));
    }

    @Test
    @DisplayName("무료 강의를 등록한다")
    void registeFreeSession() {
        Session session = SessionFactory.createSession(sessionInfoReady, SessionImagesTest.SESSION_IMAGE, new SessionPrice(0), 0, SessionType.FREE);

        //  when(sessionRepository.save(any(Session.class))).thenReturn(1);

        sessionService.registerPaidSession(sessionInfoReady, SessionImagesTest.SESSION_IMAGE, new SessionPrice(0), 2, SessionType.FREE);

        verify(sessionRepository).saveRegisterSession(any(FreeSession.class));
    }

    @Test
    @DisplayName("강의 등록시 이미지를 입력하지 않으면 오류")
    void registeSession_Illegal() {

        assertThatThrownBy(() -> {
            SessionFactory.createSession(sessionInfoReady, null, new SessionPrice(0), 0, SessionType.FREE);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageStartingWith("강의 이미지를 입력해주세요");

        assertThatThrownBy(() -> {
            sessionService.registerPaidSession(sessionInfoReady, new SessionImages(Collections.EMPTY_LIST), new SessionPrice(0), 2, SessionType.FREE);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageStartingWith("이미지는 한개 이상 이여야 합니다.");

    }

    @Test
    @DisplayName("강의 조회 테스트")
    void findSessionInfoById() throws CannotRegisteSessionException {
        Session paidSession = SessionFactory.createSession(sessionInfoReady, SessionImagesTest.SESSION_IMAGE, new SessionPrice(1000), 2, SessionType.PAID);

        when(sessionRepository.findSessionInfoById(1L)).thenReturn(paidSession);

        Session session = sessionService.findSessionInfoById(1);

        assertThat(session).isEqualTo(paidSession);
    }

    @Test
    @DisplayName("유료 강의 신청 테스트 pass")
    void orderPaidSession() throws CannotRegisteSessionException {
        Session paidSession = SessionFactory.createSession(sessionInfo, SessionImagesTest.SESSION_IMAGE, new SessionPrice(1000), 2, SessionType.PAID);
        Students students = new Students(List.of(new NsUser(1L)));

        when(sessionRepository.findSessionInfoById(1L)).thenReturn(paidSession);
        when(sessionOrderRepository.findOrderInfoBySessionId(1L)).thenReturn(students);

        Session session = sessionService.orderSession(payment, NsUserTest.SANJIGI, 1);

        assertThat(session.getStudentsSize()).isEqualTo(2);
        assertThat(session.getStudentIdx(0)).isEqualTo(new NsUser(1L));
        assertThat(session.getStudentIdx(1)).isEqualTo(NsUserTest.SANJIGI);
    }

    @Test
    @DisplayName("유료 강의 신청 시 중복 신청으로 오류")
    void orderPaidSession_duplicate() {
        Session paidSession = SessionFactory.createSession(sessionInfo, SessionImagesTest.SESSION_IMAGE, new SessionPrice(1000), 2, SessionType.PAID);
        Students students = new Students(List.of(new NsUser(2L)));

        when(sessionRepository.findSessionInfoById(1L)).thenReturn(paidSession);
        when(sessionOrderRepository.findOrderInfoBySessionId(1L)).thenReturn(students);

        assertThatThrownBy(() -> {
            sessionService.orderSession(payment, NsUserTest.SANJIGI, 1);
        }).isInstanceOf(CannotRegisteSessionException.class).hasMessageStartingWith("강의는 중복 신청할 수 없습니다.");
    }

    @Test
    @DisplayName("유료 강의 신청 시 인원 초과 오류")
    void orderPaidSession_maxStudent() {
        Session paidSession = SessionFactory.createSession(sessionInfo, SessionImagesTest.SESSION_IMAGE, new SessionPrice(1000), 2, SessionType.PAID);
        Students students = new Students(List.of(new NsUser(3L), new NsUser(4L)));

        when(sessionRepository.findSessionInfoById(1L)).thenReturn(paidSession);
        when(sessionOrderRepository.findOrderInfoBySessionId(1L)).thenReturn(students);

        assertThatThrownBy(() -> {
            sessionService.orderSession(payment, NsUserTest.SANJIGI, 1);
        }).isInstanceOf(CannotRegisteSessionException.class).hasMessageStartingWith("최대인원 수를 초과하였습니다.");
    }

    @Test
    @DisplayName("무료 강의 신청 시 pass")
    void orderFreeSession() throws CannotRegisteSessionException {
        Session freeSession = SessionFactory.createSession(sessionInfo, SessionImagesTest.SESSION_IMAGE, new SessionPrice(0), 0, SessionType.FREE);
        Students students = new Students(List.of(new NsUser(3L), new NsUser(4L), new NsUser(5L)));

        when(sessionRepository.findSessionInfoById(1L)).thenReturn(freeSession);
        when(sessionOrderRepository.findOrderInfoBySessionId(1L)).thenReturn(students);

        sessionService.orderSession(payment, NsUserTest.JAVAJIGI, 1);
    }

    @Test
    @DisplayName("무료 강의 신청 시 중복 신청으로 오류")
    void orderFreeSession_duplicate() {
        Session freeSession = SessionFactory.createSession(sessionInfo, SessionImagesTest.SESSION_IMAGE, new SessionPrice(0), 0, SessionType.FREE);
        Students students = new Students(List.of(new NsUser(1L), new NsUser(2L), new NsUser(3L)));

        when(sessionRepository.findSessionInfoById(1L)).thenReturn(freeSession);
        when(sessionOrderRepository.findOrderInfoBySessionId(1L)).thenReturn(students);

        assertThatThrownBy(() -> {
            sessionService.orderSession(payment, NsUserTest.JAVAJIGI, 1);
        })
                .isInstanceOf(CannotRegisteSessionException.class).hasMessageStartingWith("강의는 중복 신청할 수 없습니다.");
    }

    @Test
    @DisplayName("대기중인 강의주문을 강사가 승인")
    void saveOrderStateSessionOrder() throws CannotApproveSessionException {
        SessionOrder sessionOrder = SessionOrderTest.SESSION_ORDER_READY;
        when(sessionOrderRepository.findSessionOrderByOrderId(Mockito.any(Long.class))).thenReturn(sessionOrder);
        when(sessionOrderRepository.saveOrderStateSessionOrder(Mockito.any(SessionOrder.class))).thenReturn(1);

        assertThat(sessionService.approveSessionOrder(new Instructor(new InstructorId(7)), 1L));
    }

    @Test
    @DisplayName("대기중이지 않은 강의주문을 강사가 승인시 오류")
    void saveOrderStateSessionOrder_CannotApproveSessionException() {
        SessionOrder sessionOrder = SessionOrderTest.SESSION_ORDER_APPROVE;
        when(sessionOrderRepository.findSessionOrderByOrderId(Mockito.any(Long.class))).thenReturn(sessionOrder);

        assertThatThrownBy(() -> sessionService.approveSessionOrder(new Instructor(new InstructorId(7)), 1L))
                .isInstanceOf(CannotApproveSessionException.class);
    }

}
