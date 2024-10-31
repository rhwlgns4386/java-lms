package nextstep.payments.infrastructure;

import nextstep.payments.domain.Payment;
import nextstep.payments.domain.PaymentRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class JdbcPaymentRepository implements PaymentRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcPaymentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Payment payment) {
        String sql = "INSERT INTO payments (id, amount, status, created_at) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, payment.getId(), payment.getAmount(), payment.getStatus().toString(), payment.getCreatedAt());
    }

    @Override
    public Optional<Payment> findById(Long id) {
        String sql = "SELECT * FROM payments WHERE id = ?";
        return jdbcTemplate.query(sql, new PaymentRowMapper(), id).stream().findFirst();
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM payments WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class PaymentRowMapper implements RowMapper<Payment> {
        @Override
        public Payment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long id = rs.getLong("id");
            Long amount = rs.getLong("amount");
            String status = rs.getString("status");
            // assuming Payment has a static method to parse status from String
            Payment.Status paymentStatus = Payment.Status.valueOf(status);
            java.sql.Timestamp createdAtTimestamp = rs.getTimestamp("created_at");
            return new Payment(id, amount, paymentStatus, createdAtTimestamp.toLocalDateTime());
        }
    }
}
