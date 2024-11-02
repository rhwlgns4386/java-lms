package nextstep.payments.infrastructure;

import nextstep.payments.domain.Payment;
import nextstep.payments.domain.PaymentRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcPaymentRepository implements PaymentRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcPaymentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Payment payment) {
        String sql = "INSERT INTO payments (id, session_id, ns_user_id, amount, created_at) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, payment.getId(), payment.getSessionId(), payment.getStudentId(), payment.getAmount(), payment.getCreatedAt());
    }
}
