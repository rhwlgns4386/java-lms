package nextstep.courses.domain.session;

import nextstep.courses.domain.session.sessionCoverImage.SessionCoverImage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static nextstep.courses.domain.session.sessionCoverImage.SessionCoverImageTest.createHeight;
import static nextstep.courses.domain.session.sessionCoverImage.SessionCoverImageTest.createRatio;
import static nextstep.courses.domain.session.sessionCoverImage.SessionCoverImageTest.createSize;
import static nextstep.courses.domain.session.sessionCoverImage.SessionCoverImageTest.createWidth;

class SessionInfoTest {

    @Test
    void create() {
        SessionCoverImage sessionCoverImage = new SessionCoverImage("jpg",
                                                                    createRatio(createWidth(300), createHeight(200)),
                                                                    createSize(1_048_575L));

        SessionInfo sessionInfo = new SessionInfo("강의1", sessionCoverImage, 1L);

        Assertions.assertThat(sessionInfo).isNotNull();
    }
}
