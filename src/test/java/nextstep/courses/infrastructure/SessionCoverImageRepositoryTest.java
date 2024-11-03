package nextstep.courses.infrastructure;

import nextstep.courses.domain.image.SessionCoverImage;
import nextstep.courses.domain.port.SessionCoverImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static nextstep.courses.domain.image.SessionCoverImage.*;
import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class SessionCoverImageRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionCoverImageRepository sessionCoverImageRepository;

    @BeforeEach
    void setUp() {
        sessionCoverImageRepository = new JdbcSessionCoverImageRepository(jdbcTemplate);
    }


    @Test
    void 이미지_저장_테스트() {
        SessionCoverImage sessionCoverImage = new SessionCoverImage.SessionCoverImageBuilder(1L).sessionId(1L).fileName("leo.png").filePath("/home/lms/image/cover/leo.png").volume(150).width(300).height(200).build();

        int count = sessionCoverImageRepository.save(sessionCoverImage);
        SessionCoverImage savedCoverImage = sessionCoverImageRepository.findById(1L);
        assertThat(sessionCoverImage.getFileName()).isEqualTo(savedCoverImage.getFileName());
    }

}
