package nextstep.courses.domain.session;

import nextstep.courses.type.SessionType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


public class SessionBuilderTest {

    @Test
    void create_free_session() {
        Session freeSession = freeSessionBuilder().build();

        assertThat(freeSession).isInstanceOf(FreeSession.class);
    }

    public static SessionBuilder freeSessionBuilder() {
        return SessionBuilder.builder()
                .name("무료강의")
                .coverImage("src/test/java/nextstep/courses/domain/session/file/image.png")
                .sessionType(SessionType.FREE)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(90));
    }

    @Test
    void create_paid_session() {
        Session paidSession = paidSessionBuilder().build();

        assertThat(paidSession).isInstanceOf(PaidSession.class);
    }

    public static SessionBuilder paidSessionBuilder() {
        return SessionBuilder.builder()
                .name("유료강의")
                .enrollment(30)
                .sessionFee(10000)
                .coverImage("src/test/java/nextstep/courses/domain/session/file/image.png")
                .sessionType(SessionType.PAID)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(90));
    }
}
