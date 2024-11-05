package nextstep.courses.domain.session;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionDateTest {

    @Test
    void 결제_가능_테스트() {
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusDays(3);
        SessionDate sessionDate = new SessionDate(start, end);

        boolean expected = sessionDate.isInclude(LocalDate.now().plusDays(2));

        assertThat(expected).isEqualTo(true);
    }
}
