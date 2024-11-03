package nextstep.courses.entity;

import nextstep.courses.domain.session.Session;
import nextstep.courses.type.RecruitState;
import nextstep.courses.type.SessionState;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionEntityTest {

    @Test
    void to_domain() {
        SessionEntity entity = getSaveEntity();
        Session session = entity.toDomain();

        assertThat(session.getCoverFilePaths())
                .containsExactly(new File("src/test/java/nextstep/courses/domain/session/file/image.png").getAbsolutePath());
    }

    public static SessionEntity getSaveEntity() {
        return getEntity(RecruitState.RECRUIT);
    }

    public static SessionEntity getEntity(RecruitState recruitState) {
        List<String> coverFilePaths = List.of("src/test/java/nextstep/courses/domain/session/file/image.png");
        return new SessionEntity("src/test/java/nextstep/courses/domain/session/file/image.png",
                coverFilePaths, SessionState.PROGRESS, recruitState, 0, 30,
                10000, LocalDateTime.now(), LocalDateTime.now());
    }
}
