package nextstep.courses.infrastructure;

import nextstep.courses.domain.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    public static final String FREE_SESSION = "FREE";
    public static final String PAID_SESSION = "PAID";
    public static final int FREE_FEE = 0;
    public static final int FREE_MAX_STUDENTS = 0;
    private final JdbcOperations jdbcTemplate;
    private final SessionImageRepository sessionImageRepository;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate, SessionImageRepository sessionImageRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionImageRepository = sessionImageRepository;
    }

    @Override
    public int save(Session session) {
        String type = FREE_SESSION;
        int maxStudents = FREE_MAX_STUDENTS;
        int fee = FREE_FEE;
        if (session instanceof PaidSession) {
            type = PAID_SESSION;
            maxStudents = ((PaidSession) session).getMaxNumOfStudents();
            fee = ((PaidSession) session).getSessionFee();
        }
        String sql = "insert into session (session_start_date, session_end_date, status, image_id, session_type, session_fee, max_student) values (? ,? ,? ,? ,? ,? ,?)";
        return jdbcTemplate.update(sql, session.getDate().getStartAt(), session.getDate().getEndAt(), session.getStatus().name(), session.getImage().getId(), type, fee, maxStudents);

    }

    @Override
    public Session findById(Long id) {
        String sql = "select id, session_start_date, session_end_date, status, image_id, session_type, session_fee, max_student from session where id = ?";
        RowMapper<Session> rowMapper = (rs, rowNum) -> {
            SessionImage sessionImage = getSessionImage(rs);
            return getSession(rs, sessionImage);
        };
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    private SessionImage getSessionImage(ResultSet rs) throws SQLException {
        Long imageId = rs.getLong("image_id");
        return sessionImageRepository.findById(imageId);
    }

    private static Session getSession(ResultSet rs, SessionImage sessionImage) throws SQLException {
        if (PAID_SESSION.equals(rs.getString("session_type"))) {
            return getPaidSession(rs, sessionImage);
        }
        return getFreeSession(rs, sessionImage);
    }

    private static PaidSession getPaidSession(ResultSet rs, SessionImage sessionImage) throws SQLException {
        return new PaidSession(
                rs.getLong("id"),
                rs.getTimestamp("session_start_date"),
                rs.getTimestamp("session_end_date"),
                sessionImage,
                rs.getString("status"),
                rs.getInt("max_student"),
                rs.getInt("session_fee")
        );
    }

    private static FreeSession getFreeSession(ResultSet rs, SessionImage sessionImage) throws SQLException {
        return new FreeSession(
                rs.getLong("id"),
                rs.getTimestamp("session_start_date"),
                rs.getTimestamp("session_end_date"),
                sessionImage,
                rs.getString("status")
        );
    }
}
