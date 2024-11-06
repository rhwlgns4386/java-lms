package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionApplyRepository;
import nextstep.courses.domain.session.SessionApply;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class SessionApplyRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionApplyRepository sessionApplyRepository;

    @BeforeEach
    void setUp() {
        sessionApplyRepository = new JdbcSessionApplyRepository(jdbcTemplate);
    }


    @DisplayName("선택되지 않은 회원")
    @Test
    void not_selection(){
        SessionApply apply = sessionApplyRepository.findById(1L);

        assertThat(apply.isSelection()).isFalse();

    }
    @DisplayName("선택된 회원")
    @Test
    void selection(){
        SessionApply apply = sessionApplyRepository.findById(5L);

        assertThat(apply.isSelection()).isTrue();

    }
}
