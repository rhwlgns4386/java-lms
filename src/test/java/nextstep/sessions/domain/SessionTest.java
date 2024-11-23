package nextstep.sessions.domain;

import nextstep.payments.domain.Payment;
import nextstep.sessions.CannotRegisterException;
import nextstep.studentsessions.domain.RegistrationApprovalStatus;
import nextstep.studentsessions.domain.StudentSession;
import nextstep.users.NsUserTestFixture;
import nextstep.users.domain.NsStudent;
import nextstep.users.domain.NsUser;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.as;

class SessionTest {
    @DisplayName("무료 강의에 대해 수강신청 진행한다.")
    @Test
    void register_FREE_Session() {
        LocalDateTime createdAt = LocalDateTime.now();
        NsUser moon = new NsUserTestFixture().createdAt(createdAt).build();
        NsStudent studentMoon = new NsStudent(moon, createdAt);
        FreeSession session = SessionTestFixture.freeSessionBuilder().id(0L).build();

        session.registerSession(moon, createdAt);

        assertThat(session)
                .extracting("studentSessions", as(InstanceOfAssertFactories.LIST))
                .contains(new StudentSession(session, studentMoon, RegistrationApprovalStatus.PENDING, createdAt));
        assertThat(session)
                .extracting("id")
                .isEqualTo(0L);
    }

    @DisplayName("유료 강의에 대해 수강신청 진행한다.")
    @Test
    void register_PAID_Session() {
        LocalDateTime createdAt = LocalDateTime.now();
        NsUser moon = new NsUserTestFixture().createdAt(createdAt).build();
        NsStudent studentMoon = new NsStudent(moon, createdAt);
        PaidSession session = SessionTestFixture.paidSessionBuilder().maxStudent(2).fee(150000).build();
        Payment payment = new Payment("TEST_PAYMENT_ID", 0L, 0L, 150000L);

        session.registerSession(moon, payment, createdAt);

        assertThat(session)
                .extracting("studentSessions", as(InstanceOfAssertFactories.LIST))
                .contains(new StudentSession(session, studentMoon, RegistrationApprovalStatus.PENDING, createdAt));
    }

    @DisplayName("유료 강의는 강의 최대 수강 인원을 초과할 수 없다.")
    @Test
    void register_PAID_Session_failed_maxStudents() {
        NsStudent student = new NsStudent(new NsUserTestFixture().build(), LocalDateTime.now());
        PaidSession session = SessionTestFixture.paidSessionBuilder().maxStudent(1).fee(150000)
                .studentSessions(new StudentSession(null, student, LocalDateTime.now())).build();
        NsUser newUser = new NsUser(1L, "sun", "5678", "sunyoonji", "sun@a.com", LocalDateTime.now(), null);
        Payment payment = new Payment("PAYMENT_TEST", 0L, 1L, 150000L);

        assertThatThrownBy(() -> session.registerSession(newUser, payment, LocalDateTime.now()))
                .isInstanceOf(CannotRegisterException.class)
                .hasMessage("유료 강의는 강의 최대 수강 인원을 초과할 수 없습니다.");
    }


    @DisplayName("유료 강의 수강신청 등록 시 로그인한 사용자와 결제한 고객 정보가 일치해야 한다.")
    @Test
    void register_PAID_Session_failed_user_not_matching() {
        PaidSession session = SessionTestFixture.paidSessionBuilder().build();
        LocalDateTime createdAt = LocalDateTime.now();
        NsUser moon = new NsUserTestFixture().id(0L).createdAt(createdAt).build();
        Payment given = new Payment("PAYMENT_TEST", 0L, 1L, 150000L);

        assertThatThrownBy(() -> session.registerSession(moon, given, createdAt))
                .isInstanceOf(CannotRegisterException.class)
                .hasMessage("로그인한 사용자와 결제한 고객 정보가 일치하지 않습니다.");
    }
}
