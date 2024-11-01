package nextstep.sessions.domain;

import nextstep.courses.domain.Course;
import nextstep.payments.domain.Payment;
import nextstep.sessions.CannotRegisterException;
import nextstep.users.domain.NsStudent;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.BDDAssertions.as;

public class SessionTest {
    @DisplayName("무료 강의에 대해 수강신청 진행한다.")
    @Test
    void register_FREE_Session() {
        LocalDateTime createdAt = LocalDateTime.now();
        NsUser moon = NsUserTest.generateNsUserTestFixture(0L, "moon", createdAt);
        NsStudent studentMoon = new NsStudent(moon, createdAt);
        Session session = generateSessionTestFixture(0L, SessionFeeStatus.FREE, new ArrayList<>());

        session.registerSession(moon, createdAt);

        assertThat(session)
                .extracting("students", as(InstanceOfAssertFactories.LIST))
                .contains(studentMoon);
    }

    @DisplayName("유료 강의에 대해 수강신청 진행한다.")
    @Test
    void register_PAID_Session() {
        LocalDateTime createdAt = LocalDateTime.now();
        NsUser moon = NsUserTest.generateNsUserTestFixture(0L, "moon", createdAt);
        NsStudent studentMoon = new NsStudent(moon, createdAt);
        Session session = generateSessionTestFixture(0L, SessionFeeStatus.FREE, new ArrayList<>());
        Payment payment = new Payment("TEST_PAYMENT_ID", 0L, 0L, 150000L);

        session.registerPaidSession(moon, payment, createdAt);

        assertThat(session)
                .extracting("students", as(InstanceOfAssertFactories.LIST))
                .contains(studentMoon);
    }

    @DisplayName("유료 강의는 강의 최대 수강 인원을 초과할 수 없다.")
    @Test
    void register_PAID_Session_failed_maxStudents() {
        LocalDateTime createdAt = LocalDateTime.now();
        NsUser moon = new NsUser(0L, "moon", "12345", "moonyoonji", "moon@a.com", createdAt, null);
        Session session = generateSessionTestFixture(0L, SessionFeeStatus.PAID, List.of(new NsStudent(moon, createdAt)));
        NsUser newUser = new NsUser(1L, "sun", "5678", "sunyoonji", "sun@a.com", createdAt, null);
        Payment payment = new Payment("PAYMENT_TEST", 0L, 1L, 150000L);

        assertThatThrownBy(() -> session.registerPaidSession(newUser, payment, createdAt))
                .isInstanceOf(CannotRegisterException.class)
                .hasMessage("유료 강의는 강의 최대 수강 인원을 초과할 수 없습니다.");
    }


    @DisplayName("유료 강의 수강신청 등록 시 로그인한 사용자와 결제한 고객 정보가 일치해야 한다.")
    @Test
    void register_PAID_Session_failed_user_not_matching() {
        Session session = generateSessionTestFixture(0L, SessionFeeStatus.PAID, new ArrayList<>());
        LocalDateTime createdAt = LocalDateTime.now();
        NsUser moon = new NsUser(0L, "moon", "12345", "moonyoonji", "moon@a.com", createdAt, null);
        Payment given = new Payment("PAYMENT_TEST", 0L, 1L, 150000L);

        assertThatThrownBy(() -> session.registerPaidSession(moon, given, createdAt))
                .isInstanceOf(CannotRegisterException.class)
                .hasMessage("로그인한 사용자와 결제한 고객 정보가 일치하지 않습니다.");
    }

    public static Session generateSessionTestFixture(Long id, SessionFeeStatus feeStatus, List<NsStudent> students) {
        Course course = new Course();
        Image image = new Image();
        return new Session(course, students, id, "TEST_SESSION_TITLE", 150000, image, 1, feeStatus,
                SessionStatus.RECRUITING, LocalDate.of(2024, 10, 31),
                LocalDate.of(2024, 11, 30),
                LocalDateTime.now(), null);
    }
}
