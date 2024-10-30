package nextstep.courses.domain.session;

import nextstep.courses.type.SessionType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;


public class SessionBuilderTest {

    @Test
    void create_free_session() {
        Session freeSession = freeSessionBuilder().build();

        assertThat(freeSession).isInstanceOf(FreeSession.class);
    }

    public static SessionBuilder freeSessionBuilder() {
        return SessionBuilder.builder()
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
                .enrollment(30)
                .sessionFee(10000)
                .coverImage("src/test/java/nextstep/courses/domain/session/file/image.png")
                .sessionType(SessionType.PAID)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(90));
    }

    @Test
    void throw_exception_if_cover_image_null() {
        SessionBuilder builder = SessionBuilder.builder()
                .sessionType(SessionType.FREE)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(90));

        assertThatIllegalArgumentException().isThrownBy(builder::build).withMessageContaining("강의 커버");
    }

    @Test
    void throw_exception_if_start_or_end_date_not_assign() {
        SessionBuilder endDate = SessionBuilder.builder()
                .coverImage("src/test/java/nextstep/courses/domain/session/file/image.png")
                .sessionType(SessionType.FREE)
                .endDate(LocalDateTime.now().plusDays(90));

        SessionBuilder startDate = SessionBuilder.builder()
                .coverImage("src/test/java/nextstep/courses/domain/session/file/image.png")
                .sessionType(SessionType.FREE)
                .startDate(LocalDateTime.now());

        assertThatIllegalArgumentException().isThrownBy(endDate::build).withMessageContaining("시작일");
        assertThatIllegalArgumentException().isThrownBy(startDate::build).withMessageContaining("종료일");
    }

    @Test
    void throw_exception_if_session_type_not_assign() {
        SessionBuilder builder = SessionBuilder.builder()
                .coverImage("src/test/java/nextstep/courses/domain/session/file/image.png")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(90));

        assertThatIllegalArgumentException().isThrownBy(builder::build).withMessageContaining("강의 타입");
    }

    @Test
    void throw_exception_if_enrollment_not_assign() {
        SessionBuilder builder = SessionBuilder.builder()
                .sessionFee(10000)
                .coverImage("src/test/java/nextstep/courses/domain/session/file/image.png")
                .sessionType(SessionType.PAID)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(90));

        assertThatIllegalArgumentException().isThrownBy(builder::build).withMessageContaining("수강 인원");
    }

    @Test
    void throw_exception_if_sessionFee_not_assign() {
        SessionBuilder builder = SessionBuilder.builder()
                .enrollment(30)
                .coverImage("src/test/java/nextstep/courses/domain/session/file/image.png")
                .sessionType(SessionType.PAID)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(90));

        assertThatIllegalArgumentException().isThrownBy(builder::build).withMessageContaining("수강료");
    }
}
