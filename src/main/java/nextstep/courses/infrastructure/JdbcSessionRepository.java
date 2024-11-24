package nextstep.courses.infrastructure;

import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionRepository;
import nextstep.courses.domain.session.entity.SessionEntity;
import nextstep.courses.domain.session.sessioncoverimage.SessionCoverImage;
import nextstep.courses.domain.session.sessioncoverimage.SessionCoverImageRepository;
import nextstep.qna.NotFoundException;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.UserRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    private final SessionRowMapper SESSION_ROW_MAPPER = new SessionRowMapper();
    private final JdbcOperations jdbcTemplate;
    private final SessionCoverImageRepository sessionCoverImageRepository;
    private final UserRepository userRepository;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate,
                                 SessionCoverImageRepository sessionCoverImageRepository,
                                 UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionCoverImageRepository = sessionCoverImageRepository;
        this.userRepository = userRepository;
    }

    @Override
    public int save(SessionEntity sessionEntity) {
        String sql = "insert into session (title, creator_id, status, enrollment_status, price, pay_type, max_student_count, cover_image_id, " +
            "start_date_time, end_date_time) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(sql,
                                   sessionEntity.getTitle(),
                                   sessionEntity.getCreatorId(),
                                   sessionEntity.getStatus(),
                                   sessionEntity.getEnrollmentStatus(),
                                   sessionEntity.getPrice(),
                                   sessionEntity.getPayType(),
                                   sessionEntity.getMaxStudentCount(),
                                   sessionEntity.getCoverImageId(),
                                   sessionEntity.getStartDateTime(),
                                   sessionEntity.getEndDateTime());
    }

    @Override
    public SessionEntity findById(long sessionId) {
        String sql = "select id, title, creator_id, status, enrollment_status, price, pay_type, max_student_count, cover_image_id, " +
            "start_date_time, end_date_time from session where id = ?";

        return jdbcTemplate.queryForObject(sql, SESSION_ROW_MAPPER, sessionId);
    }

    @Override
    public Session findByIdForSession(long sessionId) {
        String selectSessionSql = "select id, title, creator_id, status, enrollment_status, price, pay_type, max_student_count, cover_image_id, " +
            "start_date_time, end_date_time from session where id = ?";

        SessionEntity sessionEntity = Optional.ofNullable(jdbcTemplate.queryForObject(selectSessionSql,
                                                                                      SESSION_ROW_MAPPER, sessionId))
                                              .orElseThrow(NotFoundException::new);

        List<NsUser> students = userRepository.findBySessionId(sessionId);

        List<SessionCoverImage> sessionCoverImages = sessionCoverImageRepository.findBySessionId(sessionId);
        Session session = sessionEntity.toSession(sessionCoverImages, students);

        return session;
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }

    private class SessionRowMapper implements RowMapper<SessionEntity> {
        @Override
        public SessionEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new SessionEntity(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getLong("creator_id"),
                rs.getString("status"),
                rs.getString("enrollment_status"),
                rs.getLong("price"),
                rs.getString("pay_type"),
                rs.getInt("max_student_count"),
                rs.getLong("cover_image_id"),
                toLocalDateTime(rs.getTimestamp("start_date_time")),
                toLocalDateTime(rs.getTimestamp("end_date_time")));
        }
    }

}
