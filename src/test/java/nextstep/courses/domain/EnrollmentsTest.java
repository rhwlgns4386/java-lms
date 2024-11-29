package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Set;
import nextstep.courses.DuplicateStudentException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class EnrollmentsTest {

    @Test
    void 이미추가된_사용자면_예외가_발생한다() {
        Enrollments enrollments = new Enrollments(SessionStatus.ENROLLING, Set.of(NsUserTest.JAVAJIGI));
        assertThatThrownBy(() -> enrollments.enrollment(NsUserTest.JAVAJIGI)).isInstanceOf(
                DuplicateStudentException.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"PREPARING", "CLOSED"})
    void 진행상태가_아니라면_예외(SessionStatus sessionStatus) {
        Enrollments enrollments = new Enrollments(sessionStatus);
        assertThatThrownBy(() -> enrollments.enrollment(NsUserTest.JAVAJIGI)).isInstanceOf(
                NonReadyException.class);
    }
}
