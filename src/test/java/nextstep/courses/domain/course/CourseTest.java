package nextstep.courses.domain.course;

import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionBuilderTest;
import nextstep.courses.type.SessionState;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

public class CourseTest {

    private static final long PAID_SESSION_ID = 1L;
    private static final long FREE_SESSION_ID = 2L;

    private Course course;
    private Session paidSession;
    private Session freeSession;

    @BeforeEach
    void init() {
        course = new Course("테스트 기수", NsUserTest.JAVAJIGI.getId());
        paidSession = SessionBuilderTest.paidSessionBuilder()
                .id(PAID_SESSION_ID).sessionState(SessionState.OPEN).build();
        freeSession = SessionBuilderTest.freeSessionBuilder()
                .id(FREE_SESSION_ID).sessionState(SessionState.OPEN).build();

        course.add(paidSession);
        course.add(freeSession);
    }

    @Test
    void add_session() {
        assertThat(course.has(paidSession)).isTrue();
        assertThat(course.has(freeSession)).isTrue();
    }

    @Test
    void throw_exception_if_try_register_invalid_session_id() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> course.registerSession(
                        new Payment("test", 3L, NsUserTest.JAVAJIGI.getId(), 10000L)));
    }

    @Test
    void succeed_to_finalize_session_registration() {
        assertThat(course.registerSession(
                new Payment("paid", PAID_SESSION_ID, NsUserTest.JAVAJIGI.getId(), 10000L))).isTrue();
        assertThat(course.registerSession(
                new Payment("free", FREE_SESSION_ID, NsUserTest.JAVAJIGI.getId(), 0L))).isTrue();
    }

    @Test
    void throw_exception_if_finalize_register_with_invalid_session_id_payment() {
        assertThatIllegalArgumentException().isThrownBy(() ->
                course.registerSession(new Payment("", 3L, NsUserTest.JAVAJIGI.getId(), 10000L)));
    }

    @Test
    void throw_exception_if_finalize_register_with_invalid_session_fee_payment() {
        assertThatIllegalStateException().isThrownBy(() ->
                course.registerSession(new Payment("", 1L, NsUserTest.JAVAJIGI.getId(), 100L)));
    }
}
