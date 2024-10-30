package nextstep.courses.domain.session;

import nextstep.courses.domain.enroll.EnrollUserInfo;
import nextstep.courses.domain.enroll.EnrollUserInfos;
import nextstep.courses.domain.image.SessionCoverImage;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

public class SessionTest {

    private SessionCoverImage sessionCoverImage;
    private EnrollUserInfos enrollUserInfos;

    private Session freeSession;

    @BeforeEach
    void setUp() {
        sessionCoverImage = new SessionCoverImage(1L, 150, "leo.png", 300, 200);
        EnrollUserInfo enrollUserInfo = new EnrollUserInfo(1L, 1L);
        enrollUserInfos = new EnrollUserInfos(20);
        enrollUserInfos.add(enrollUserInfo);
        freeSession = Session.createSessionOf(1L, 0L, SessionPriceType.FREE, SessionStatus.PROGRESS, sessionCoverImage, LocalDateTime.of(2024, 10, 30, 10, 30), LocalDateTime.of(2024, 11, 20, 10, 30), 30);

    }

    @Test
    void 무료_강의_저장_테스트() {
        freeSession.enroll(NsUserTest.JAVAJIGI, 10000);

        assertThat(freeSession).isEqualTo(Set.of(new EnrollUserInfo(1L, 1L)));
    }



}
