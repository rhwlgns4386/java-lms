package nextstep.courses.domain.session.sessioncoverimage;

import nextstep.courses.infrastructure.JdbcSessionCoverImageRepository;
import nextstep.qna.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@JdbcTest
class SessionCoverImageRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionCoverImageRepository sessionCoverImageRepository;

    @BeforeEach
    void setUp() {
        sessionCoverImageRepository = new JdbcSessionCoverImageRepository(jdbcTemplate);
    }

    @Test
    void findById_성공() {
        long coverImageId = 3L;

        SessionCoverImage sessionCoverImage = sessionCoverImageRepository.findById(coverImageId);

        Assertions.assertThat(sessionCoverImage).isNotNull();
    }

    @Test
    void findById_실패() {
        long coverImageId = 4L;

        Assertions.assertThatThrownBy(() -> {
            sessionCoverImageRepository.findById(coverImageId);
        }).isInstanceOf(NotFoundException.class);
    }

    @Test
    void findBySessionId() {
        long sessionId1 = 1L;
        List<SessionCoverImage> sessionCoverImageList1 = sessionCoverImageRepository.findBySessionId(sessionId1);

        Assertions.assertThat(sessionCoverImageList1).isNotNull();
        Assertions.assertThat(sessionCoverImageList1.size()).isEqualTo(2);

        long sessionId3 = 3L;
        List<SessionCoverImage> sessionCoverImageList3 = sessionCoverImageRepository.findBySessionId(sessionId3);

        Assertions.assertThat(sessionCoverImageList3).isEmpty();
    }

}