package nextstep.sessions.infrastructure;

import nextstep.courses.domain.Course;
import nextstep.sessions.domain.ImageType;
import nextstep.sessions.domain.MaximumEnrollment;
import nextstep.sessions.domain.Period;
import nextstep.sessions.domain.PriceType;
import nextstep.sessions.domain.Session;
import nextstep.sessions.domain.SessionImage;
import nextstep.sessions.domain.SessionRepository;
import nextstep.sessions.domain.SessionStatus;
import nextstep.sessions.domain.SessionType;
import nextstep.util.DateUtil;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Session session) {
        String sql = "insert into session (id, start_date, end_date, type, maximum_enrollment, tuition, status, created_at, updated_at, image_id, course_id)" +
                " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, session.getId(), session.getStartDate(), session.getEndDate(), session.getType()
                , session.getMaximumEnrollment(), session.getTuition(), session.getStatus(), session.getCreatedAt(), session.getUpdatedAt()
                , session.getImageId(), session.getCourseId());
    }

    @Override
    public Optional<Session> findById(Long id) {
        String query = "select s.id, s.start_date, s.end_date, s.type, s.maximum_enrollment, s.tuition, s.status, s.created_at, s.updated_at" +
                ", s.image_id, s.course_id" +
                " from session s" +
                " where s.id = ?";
        RowMapper<Session> rowMapper = (rs, rowNum) -> new Session(
                rs.getLong(1)
                , new SessionImage(rs.getLong(10))
                , new Period(DateUtil.toLocalDate(rs.getTimestamp(2)), DateUtil.toLocalDate(rs.getTimestamp(3)))
                , new SessionType(PriceType.of(rs.getString(4)), new MaximumEnrollment(rs.getInt(5)), rs.getInt(6))
                , SessionStatus.of(rs.getString(7))
                , new Course(rs.getLong(11))
                , DateUtil.toLocalDateTime(rs.getTimestamp(8))
                , DateUtil.toLocalDateTime(rs.getTimestamp(9))
        );
        return Optional.of(jdbcTemplate.queryForObject(query, rowMapper, id));
    }

    @Override
    public int update(Session session) {
        String sql = "update session" +
                " set id = ?, start_date = ?, end_date = ?, type = ?, maximum_enrollment = ?, tuition = ?, status = ?" +
                ", image_id = ?, course_id = ?" +
                " where id = ?";
        return jdbcTemplate.update(sql, session.getId(), session.getStartDate(), session.getEndDate(), session.getType()
                , session.getMaximumEnrollment(), session.getTuition(), session.getStatus()
                , session.getImageId(), session.getCourseId(), session.getId());
    }
}
