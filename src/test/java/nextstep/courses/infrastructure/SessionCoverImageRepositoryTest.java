package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionCoverImage;
import nextstep.courses.domain.SessionCoverImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class SessionCoverImageRepositoryTest {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SessionCoverImageRepository sessionCoverImageRepository;

    @BeforeEach
    void setUp() {
        sessionCoverImageRepository = new JdbcSessionCoverImageRepository(namedParameterJdbcTemplate);
    }

    @Test
    void name() {
        int count = sessionCoverImageRepository.save(new SessionCoverImage(1L, 500 * 1024, "jpg", 300, 200));
        assertThat(count).isEqualTo(1);
    }
}