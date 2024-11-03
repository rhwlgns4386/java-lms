package nextstep.courses.infrastructure.cover;

import nextstep.courses.entity.CoverImageEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class JdbcCoverImageRepositoryTest {

    @Autowired
    private JdbcOperations jdbcTemplate;

    private JdbcCoverImageRepository coverImageRepository;

    @BeforeEach
    public void init() {
        this.coverImageRepository = new JdbcCoverImageRepository(jdbcTemplate);
    }

    @Test
    void insert() {
        CoverImageEntity coverImageEntity =
                new CoverImageEntity("src/test/java/nextstep/courses/domain/session/file/image.png");

        coverImageRepository.save(coverImageEntity, 1L);
        List<CoverImageEntity> results = coverImageRepository.findBySessionId(1L);

        assertThat(results).hasSize(2);
        assertThat(results.get(1).getFilePath())
                .isEqualTo("src/test/java/nextstep/courses/domain/session/file/image.png");
    }
}
