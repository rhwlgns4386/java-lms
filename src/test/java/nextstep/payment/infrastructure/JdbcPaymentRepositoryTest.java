package nextstep.payment.infrastructure;

import nextstep.payments.domain.Payment;
import nextstep.payments.infrastructure.JdbcPaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

public class JdbcPaymentRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private JdbcPaymentRepository jdbcPaymentRepository;

    private Payment payment;
private static final String INSERT_SQL = "INSERT INTO payments (id, session_id, ns_user_id, amount, created_at) VALUES (?, ?, ?, ?, ?)";
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        payment = new Payment("1", 200L, 300L, 15000L, 1L);
    }

    @Test
    public void 결제_저장() {
        jdbcPaymentRepository.save(payment);
        verify(jdbcTemplate).update(
                eq(INSERT_SQL),
                any(),
                any(),
                any(),
                any(),
                any());
    }
}
