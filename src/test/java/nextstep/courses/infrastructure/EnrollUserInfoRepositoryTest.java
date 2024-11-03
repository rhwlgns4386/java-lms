package nextstep.courses.infrastructure;

import nextstep.courses.domain.enroll.EnrollUserInfo;
import nextstep.courses.domain.port.EnrollUserInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class EnrollUserInfoRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private EnrollUserInfoRepository enrollUserInfoRepository;


    @BeforeEach
    void setUp() {
        enrollUserInfoRepository = new JdbcEnrollUserInfoRepository(jdbcTemplate);
    }


    @Test
    void 강의_등록_이력_저장_테스트() {

        EnrollUserInfo enrollUserInfo = new EnrollUserInfo(1L, 1L);
        int count = enrollUserInfoRepository.save(enrollUserInfo);
        EnrollUserInfo savedEnrollUserInfo = enrollUserInfoRepository.findBySessionIdAndNsUserId(1L, 1L);

        assertThat(enrollUserInfo.getSessionId()).isEqualTo(savedEnrollUserInfo.getSessionId());
        assertThat(enrollUserInfo.getNsUserId()).isEqualTo(savedEnrollUserInfo.getNsUserId());

    }
}
