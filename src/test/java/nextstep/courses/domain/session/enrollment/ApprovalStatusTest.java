package nextstep.courses.domain.session.enrollment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ApprovalStatusTest {

    @Test
    void is_approved() {
        Assertions.assertThat(ApprovalStatus.isApproved("APPROVED")).isTrue();
        Assertions.assertThat(ApprovalStatus.isApproved("DISAPPROVED")).isFalse();
    }

    @Test
    void is_disapproved() {
        Assertions.assertThat(ApprovalStatus.isDisapproved("DISAPPROVED")).isTrue();
        Assertions.assertThat(ApprovalStatus.isDisapproved("APPROVED")).isFalse();

    }
}