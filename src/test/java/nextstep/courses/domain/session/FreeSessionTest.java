package nextstep.courses.domain.session;

import nextstep.courses.domain.image.SessionCoverImage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class FreeSessionTest {

    @Test
    void 무료_강의_저장_테스트() {
        SessionCoverImage sessionCoverImage = new SessionCoverImage(1L, 150, "leo.pdf", 300, 200);
        FreeSession freeSession = new FreeSession(sessionCoverImage, CourseStatus.COMPLETED, 100);


        Assertions.assertThat(freeSession).isEqualTo(new FreeSession(new SessionCoverImage(1L, 150, "leo.pdf", 300, 200), CourseStatus.COMPLETED, 100L));
    }

}
