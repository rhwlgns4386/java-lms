package nextstep.enrollment.infrastructure;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import nextstep.enrollment.domain.ApprovalStatus;
import nextstep.enrollment.domain.Enrollment;
import nextstep.enrollment.domain.EnrollmentRepository;
import nextstep.enrollment.domain.EnrollmentStatus;
import nextstep.payments.domain.Payment;

@Repository("jdbcEnrollmentRepository")
public class JdbcEnrollmentRepository implements EnrollmentRepository {

    private final JdbcOperations jdbcTemplate;

    public JdbcEnrollmentRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Enrollment> findById(Long id) {
        String sql =
            "SELECT e.id, e.session_id, e.user_id, e.enrollment_date, e.payment_id, e.approval_status, e.enrollment_status, "
                + " p.ns_user_id, p.amount, p.created_at "
                + "FROM enrollment e "
                + "INNER JOIN payment p ON e.payment_id = p.id "
                + "WHERE e.id = ?";

        RowMapper<Enrollment> rowMapper = (rs, rowNum) -> {
            Payment payment = new Payment(
                rs.getLong("payment_id"),
                rs.getLong("session_id"),
                rs.getLong("ns_user_id"),
                rs.getLong("amount"),
                rs.getObject("created_at", LocalDateTime.class)
            );
            return new Enrollment(
                rs.getLong("id"),
                rs.getLong("session_id"),
                rs.getLong("user_id"),
                rs.getTimestamp("enrollment_date").toLocalDateTime(),
                payment,
                ApprovalStatus.valueOf(rs.getString("approval_status")),
                EnrollmentStatus.valueOf(rs.getString("enrollment_status"))
            );
        };

        // 결과가 없으면 null을 반환하도록 Optional.empty() 대신 null 반환
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    @Override
    public Enrollment save(Enrollment enrollment) {
        String sql =
            "INSERT INTO enrollment (session_id, user_id, enrollment_date, payment_id, approval_status, enrollment_status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
            ps.setLong(1, enrollment.getSessionId());
            ps.setLong(2, enrollment.getNsUserId());
            ps.setTimestamp(3, Timestamp.valueOf(enrollment.getEnrollmentDate()));
            ps.setString(4, getPaymentId(enrollment));
            ps.setString(5, enrollment.getApprovalStatus().name());
            ps.setString(6, enrollment.getEnrollmentStatus().name());
            return ps;
        }, keyHolder);

        long generatedId = keyHolder.getKey().longValue();

        return new Enrollment(
            generatedId,
            enrollment.getSessionId(),
            enrollment.getNsUserId(),
            enrollment.getEnrollmentDate(),
            enrollment.getPayment(),
            enrollment.getApprovalStatus(),
            enrollment.getEnrollmentStatus()
        );
    }

    private static String getPaymentId(Enrollment enrollment) {
        if (enrollment == null) {
            return null;
        }
        return "1234";
    }
}
