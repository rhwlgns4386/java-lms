package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class SessionStatusTest {

    @ParameterizedTest
    @CsvSource(value = {"PREPARING,false", "ENROLLING,true", "CLOSED,false"})
    void 현재상태가_진행중인지_확인한다(SessionStatus sessionStatus, boolean result) {
        assertThat(sessionStatus.isEnrolling()).isEqualTo(result);
    }

}
