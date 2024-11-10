package nextstep.enrollment.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

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
import nextstep.payments.service.PaymentService;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionPolicy;
import nextstep.session.domain.SessionRepository;
import nextstep.session.domain.fixture.FixtureEnrollmentFactory;
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

    private static Session getRecruitingFreeSession(Long sessionId) {
        Session session = FixtureSessionFactory.createFreeSession(sessionId);
        session.startRecruitment();
        return session;
    }

}