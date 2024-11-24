package nextstep.courses.domain.session.enrollment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class EnrollmentStatusTest {

    @Test
    void is_possible_or_impossible() {
        Assertions.assertThat(EnrollmentStatus.POSSIBLE.isPossible()).isTrue();
        Assertions.assertThat(EnrollmentStatus.POSSIBLE.isImPossible()).isFalse();

        Assertions.assertThat(EnrollmentStatus.IMPOSSIBLE.isImPossible()).isTrue();
        Assertions.assertThat(EnrollmentStatus.IMPOSSIBLE.isPossible()).isFalse();
    }
}