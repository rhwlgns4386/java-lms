package nextstep.enrollment.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nextstep.enrollment.domain.ApprovalStatus;
import nextstep.enrollment.domain.Enrollment;
import nextstep.enrollment.domain.EnrollmentRepository;
import nextstep.enrollment.domain.EnrollmentStatus;
import nextstep.payments.domain.Payment;
import nextstep.payments.service.PaymentService;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionPolicy;
import nextstep.session.domain.SessionRepository;
import nextstep.session.domain.SessionType;
import nextstep.session.domain.fixture.FixtureEnrollmentFactory;
import nextstep.session.domain.fixture.FixturePaymentFactory;
import nextstep.session.domain.fixture.FixtureSessionFactory;
import nextstep.users.domain.NsUserTest;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {

    @InjectMocks
    private EnrollmentService enrollmentService;
    @Mock
    private PaymentService paymentService;
    @Mock
    private EnrollmentRepository enrollmentRepository;
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private List<SessionPolicy> sessionPolicies;

    @Test
    @DisplayName("enrollment 메서드가 무료 강의를 수강신청할 때 신청된 수강신청 정보를 반환한다.")
    void enrollmentByFreeSessionTest() {
        // given
        Long sessionId = 1L;
        Session mockSession = getRecruitingFreeSession(sessionId);
        Enrollment mockEnrollment = FixtureEnrollmentFactory.createEnrollmentByFreeSession(mockSession);
        given(sessionRepository.findById(sessionId)).willReturn(Optional.of(mockSession));
        given(enrollmentRepository.save(any(Enrollment.class))).willReturn(mockEnrollment);

        // when
        Enrollment enrollment = enrollmentService.enrollment(NsUserTest.JAVAJIGI, sessionId);

        // then
        assertThat(enrollment.getPayment()).isNull();
        assertThat(enrollment.getId()).isEqualTo(mockEnrollment.getId());
        assertThat(enrollment.getSessionId()).isEqualTo(mockEnrollment.getSessionId());
        assertThat(enrollment.getNsUserId()).isEqualTo(mockEnrollment.getNsUserId());
        assertThat(enrollment.getEnrollmentDate()).isEqualTo(mockEnrollment.getEnrollmentDate());
        assertThat(enrollment.getApprovalStatus()).isEqualTo(ApprovalStatus.NOT_APPROVED);
        assertThat(enrollment.getEnrollmentStatus()).isEqualTo(EnrollmentStatus.ACTIVE);
        then(sessionRepository).should(times(1)).findById(sessionId);
        then(enrollmentRepository).should(times(1)).save(any(Enrollment.class));
        then(paymentService).should(never()).getPaymentHistory(NsUserTest.JAVAJIGI, sessionId);
    }

    @Test
    @DisplayName("enrollment 메서드가 유료 강의를 수강신청할 때 신청된 수강신청 정보를 반환한다.")
    void enrollmentByPaidSessionTest() {
        // given
        Long sessionId = 1L;
        Session mockSession = getRecruitingPaidSession(sessionId);
        Enrollment mockEnrollment = getEnrollmentByPaidSession(mockSession);
        given(sessionRepository.findById(sessionId)).willReturn(Optional.of(mockSession));
        given(enrollmentRepository.save(any(Enrollment.class))).willReturn(mockEnrollment);

        // when
        Enrollment enrollment = enrollmentService.enrollment(NsUserTest.JAVAJIGI, sessionId);

        // then
        assertThat(enrollment.getPayment()).isNotNull();
        assertThat(enrollment.getId()).isEqualTo(mockEnrollment.getId());
        assertThat(enrollment.getSessionId()).isEqualTo(mockEnrollment.getSessionId());
        assertThat(enrollment.getNsUserId()).isEqualTo(mockEnrollment.getNsUserId());
        assertThat(enrollment.getEnrollmentDate()).isEqualTo(mockEnrollment.getEnrollmentDate());
        assertThat(enrollment.getApprovalStatus()).isEqualTo(ApprovalStatus.NOT_APPROVED);
        assertThat(enrollment.getEnrollmentStatus()).isEqualTo(EnrollmentStatus.ACTIVE);
        then(sessionRepository).should(times(1)).findById(sessionId);
        then(enrollmentRepository).should(times(1)).save(any(Enrollment.class));
        then(paymentService).should(times(1)).getPaymentHistory(NsUserTest.JAVAJIGI, sessionId);
    }

    // TODO : 테스트 실패중. 서비스의 전략 패턴 메서드 호출 검증 방법 점검 필요.
    @Test
    @DisplayName("approve 메서드가 enrollment 유효성 검사 및 enrollment의 approve 메서드를 호출한다.")
    void approveTest() {
        //given
        Long enrollmentId = 1L;
        Enrollment mockEnrollment = mock(Enrollment.class);
        given(enrollmentRepository.findById(enrollmentId)).willReturn(Optional.of(mockEnrollment));
        given(mockEnrollment.getSessionType()).willReturn(SessionType.PAID);

        SessionPolicy mockSessionPolicy1 = mock(SessionPolicy.class);
        SessionPolicy mockSessionPolicy2 = mock(SessionPolicy.class);
        given(mockSessionPolicy1.isMatch(SessionType.PAID)).willReturn(false);
        given(mockSessionPolicy2.isMatch(SessionType.PAID)).willReturn(true);

        List<SessionPolicy> mockSessionPolicies = Arrays.asList(
            mockSessionPolicy1,
            mockSessionPolicy2
        );
        given(sessionPolicies.stream()).willReturn(mockSessionPolicies.stream());

        //when
        enrollmentService.approve(enrollmentId);

        //then
        then(enrollmentRepository).should(times(1)).findById(enrollmentId);
        then(mockEnrollment).should(times(1)).approve();
        then(mockSessionPolicy1).should(never()).validatePolicy(mockEnrollment);
        then(mockSessionPolicy2).should(times(1)).validatePolicy(mockEnrollment);
    }

    private static Session getRecruitingFreeSession(Long sessionId) {
        Session session = FixtureSessionFactory.createFreeSession(sessionId);
        session.startRecruitment();
        return session;
    }

    private static Session getRecruitingPaidSession(Long sessionId) {
        Session session = FixtureSessionFactory.createPaidSession(sessionId, 1L, 5_000L);
        session.startRecruitment();
        return session;
    }

    private static Enrollment getEnrollmentByPaidSession(Session session) {
        Payment mockPayment = FixturePaymentFactory.create(1L, 1L, 1L, 5_000L);
        return FixtureEnrollmentFactory.createEnrollmentByPaidSession(session, mockPayment);
    }

}