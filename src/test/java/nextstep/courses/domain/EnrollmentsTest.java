package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Set;
import nextstep.courses.DuplicateStudentException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

public class EnrollmentsTest {

    @Test
    void 이미추가된_사용자면_예외가_발생한다() {
        Enrollments enrollments = new Enrollments(Set.of(NsUserTest.JAVAJIGI));
        assertThatThrownBy(() -> enrollments.enrollment(NsUserTest.JAVAJIGI)).isInstanceOf(
                DuplicateStudentException.class);
    }
}
