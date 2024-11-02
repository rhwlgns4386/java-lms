package nextstep.users.infrastructure;

import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class JdbcUserRepositoryTest {

    @Mock
    private JdbcOperations jdbcTemplate;

    @InjectMocks
    private JdbcUserRepository userRepository;

    private static final String SELECT_USER_BY_USER_ID = "select id, user_id, password, name, email, created_at, updated_at from ns_user where user_id = ?";
    private NsUser user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new NsUser(1L, "testUser", "password", "Test User", "test@example.com", LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    public void 사용자_ID로_사용자를_조회한다() {
        String userId = "testUser";

        when(jdbcTemplate.queryForObject(eq(SELECT_USER_BY_USER_ID), any(), (RowMapper<Object>) any())).thenReturn(user);

        Optional<NsUser> foundUser = userRepository.findByUserId(userId);

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get()).isEqualTo(user);
    }

    @Test
    public void 사용자_ID로_사용자가_존재하지_않는_경우() {
        String userId = "nonExistentUser";
        String sql = "select id, user_id, password, name, email, created_at, updated_at from ns_user where user_id = ?";

        when(jdbcTemplate.queryForObject(sql, any(), any())).thenThrow(new org.springframework.dao.EmptyResultDataAccessException(1));

        Optional<NsUser> foundUser = userRepository.findByUserId(userId);

        assertThat(foundUser).isNotPresent();
    }
}
