package nextstep.payments.infrastructure;

import nextstep.payments.domain.Payment;
import nextstep.payments.domain.PaymentRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcPaymentRepository implements PaymentRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final String INSERT_SQL = "INSERT INTO payments (id, session_id, ns_user_id, amount, created_at) VALUES (?, ?, ?, ?, ?)";

    public JdbcPaymentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Payment payment) {
        jdbcTemplate.update(INSERT_SQL, payment.getId(), payment.getSessionId(), payment.getStudentId(), payment.getAmount(), payment.getCreatedAt());
    }
}
