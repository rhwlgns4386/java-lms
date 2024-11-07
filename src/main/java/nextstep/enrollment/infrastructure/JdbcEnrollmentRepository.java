package nextstep.enrollment.infrastructure;

import java.sql.PreparedStatement;
import java.sql.Timestamp;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import nextstep.enrollment.domain.Enrollment;
import nextstep.enrollment.domain.EnrollmentRepository;

@Repository("jdbcEnrollmentRepository")
public class JdbcEnrollmentRepository implements EnrollmentRepository {

    private final JdbcOperations jdbcTemplate;

    public JdbcEnrollmentRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
            enrollment.getSession(),
            enrollment.getNsUser(),
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
