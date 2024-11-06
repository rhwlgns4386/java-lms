package nextstep.payments.infrastructure;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import nextstep.payments.domain.Payment;
import nextstep.payments.domain.PaymentRepository;

@Repository("jdbcPaymentRepository")
public class JdbcPaymentRepository implements PaymentRepository {

    private final JdbcOperations jdbcTemplate;

    public JdbcPaymentRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Payment> findBySessionIdAndUserId(Long sessionId, Long userId) {
        String sql =
            "SELECT id, session_id, ns_user_id, amount, created_at "
                + "FROM payment "
                + "WHERE session_id = ? "
                + "AND ns_user_id = ?";

        RowMapper<Payment> rowMapper = (rs, rowNum) -> new Payment(
            rs.getLong("id"), // id를 추가로 가져오기
            rs.getLong("session_id"),
            rs.getLong("ns_user_id"),
            rs.getLong("amount"),
            rs.getObject("created_at", LocalDateTime.class)
        );

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, sessionId, userId));
    }

}
