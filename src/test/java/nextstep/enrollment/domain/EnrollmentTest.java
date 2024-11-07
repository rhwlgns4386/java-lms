package nextstep.enrollment.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.session.domain.Session;
import nextstep.session.domain.fixture.FixtureSessionFactory;
import nextstep.users.domain.NsUserTest;

class EnrollmentTest {

    @Test
    @DisplayName("수강신청한 사람 중 선발되지 않은 사람은 수강을 취소할 수 있다.")
    void cancelTest() {
        Session session = FixtureSessionFactory.createFreeSession(1L);
        Enrollment enrollment = Enrollment.free(1L, session, NsUserTest.JAVAJIGI);
        enrollment.reject();
        assertDoesNotThrow(() -> enrollment.cancel());
    }

    @Test
    @DisplayName("수강신청한 사람 중 승인된 사람이 취소할 수 경우 예외가 발생한다.")
    void throwExceptionWhen() {
        Session session = FixtureSessionFactory.createFreeSession(1L);
        Enrollment enrollment = Enrollment.free(1L, session, NsUserTest.JAVAJIGI);
        enrollment.approve();
        Assertions.assertThatThrownBy(() -> enrollment.cancel())
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("수강신청 승인되어 취소할 수 없습니다.");
    }
}