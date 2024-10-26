package nextstep.courses.domain;

import nextstep.courses.domain.session.EnrolledUsers;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class EnrolledUsersTest {

    @Test
    void 등록실패_이미_등록() {
        EnrolledUsers enrolledUsers = new EnrolledUsers();
        enrolledUsers.add(NsUserTest.JAVAJIGI);
        assertThat(enrolledUsers.isAlreadyEnrolled(NsUserTest.JAVAJIGI)).isTrue();
    }
}
