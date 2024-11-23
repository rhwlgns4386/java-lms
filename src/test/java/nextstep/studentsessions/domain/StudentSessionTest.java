package nextstep.studentsessions.domain;

import nextstep.sessions.domain.FreeSession;
import nextstep.sessions.domain.SessionTestFixture;
import nextstep.users.NsUserTestFixture;
import nextstep.users.domain.NsStudent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class StudentSessionTest {

    @DisplayName("최초 StudentSession 객체를 생성하면 승인 상태는 PENDING 상태이다.")
    @Test
    void create() {
        NsStudent studentMoon = new NsStudent(new NsUserTestFixture().build(), LocalDateTime.now());
        FreeSession session = SessionTestFixture.freeSessionBuilder().build();

        StudentSession studentSession = new StudentSession(session, studentMoon, LocalDateTime.now());

        assertThat(studentSession)
                .extracting("session", "student", "registrationApprovalStatus")
                .contains(session, studentMoon, RegistrationApprovalStatus.PENDING);
    }

    @DisplayName("수강신청을 승인하면 승인 상태를 APPROVED로 업데이트한다.")
    @Test
    void approve() {
        StudentSession studentSession = new StudentSession(
                SessionTestFixture.freeSessionBuilder().build(),
                new NsStudent(new NsUserTestFixture().build(), LocalDateTime.now()),
                LocalDateTime.now()
        );

        studentSession.approve();

        assertThat(studentSession).extracting("registrationApprovalStatus")
                .isEqualTo(RegistrationApprovalStatus.APPROVED);
    }

    @DisplayName("수강신청을 취소하면 승인 상태를 REJECTED로 업데이트한다.")
    @Test
    void reject() {
        StudentSession studentSession = new StudentSession(
                SessionTestFixture.freeSessionBuilder().build(),
                new NsStudent(new NsUserTestFixture().build(), LocalDateTime.now()),
                LocalDateTime.now()
        );

        studentSession.reject();

        assertThat(studentSession).extracting("registrationApprovalStatus")
                .isEqualTo(RegistrationApprovalStatus.REJECTED);
    }
}
