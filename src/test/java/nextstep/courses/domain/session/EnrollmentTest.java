package nextstep.courses.domain.session;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class EnrollmentTest {

    @Test
    void create() {
        Session session = new Session(1000000, "jpg", 300, 200, SessionState.RECRUITING, (o) -> true, LocalDate.now(), LocalDate.now().plusDays(3));
        Enrollment expected = new Enrollment(NsUserTest.JAVAJIGI, session);

        assertThat(expected).isEqualTo(new Enrollment(NsUserTest.JAVAJIGI, session));
    }


}
