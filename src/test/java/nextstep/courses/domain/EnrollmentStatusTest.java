package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class EnrollmentStatusTest {

    @ParameterizedTest
    @CsvSource(value = {"PREPARING,NOT_RECRUITING", "PROGRESS,RECRUITING",
            "CLOSED,NOT_RECRUITING"})
    void 세션상태를기준으로_모집상태를_찾는다(SessionStatus sessionStatus, EnrollmentStatus enrollmentStatus) {
        assertThat(EnrollmentStatus.findBySessionStatus(sessionStatus)).isEqualTo(enrollmentStatus);
    }
}
