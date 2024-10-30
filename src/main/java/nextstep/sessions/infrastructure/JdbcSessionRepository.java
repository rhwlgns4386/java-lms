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
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Session session) {
        String imageSql = "insert into session_image (size, type, width, height) values (?, ?, ?, ?)";
        KeyHolder keyHolderForImage = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(imageSql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, session.getImageSize());
            ps.setString(2, session.getImageType());
            ps.setDouble(3, session.getImageWidth());
            ps.setDouble(4, session.getImageHeight());
            return ps;
        }, keyHolderForImage);
        Long imageId = keyHolderForImage.getKey().longValue();

        String sql = "insert into session (id, start_date, end_date, type, maximum_enrollment, tuition, status, created_at, updated_at, image_id, course_id)" +
                " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, session.getId(), session.getStartDate(), session.getEndDate(), session.getType()
                , session.getMaximumEnrollment(), session.getTuition(), session.getStatus(), session.getCreatedAt(), session.getUpdatedAt()
                , imageId, session.getCourseId());
    }

    @Override
    public Optional<Session> findById(Long id) {
        String query = "select s.id, s.start_date, s.end_date, s.type, s.maximum_enrollment, s.tuition, s.status, s.created_at, s.updated_at" +
                ", s.image_id, si.size, si.type as image_type, si.width, si.height" +
                ", c.id, c.title, c.creater_id, c.created_at, c.updated_at" +
                " from session s" +
                " join session_image si on s.image_id = si.id" +
                " join course c on c.id = s.course_id" +
                " where s.id = ?";
        RowMapper<Session> rowMapper = (rs, rowNum) -> new Session(
                rs.getLong(1),
                new SessionImage(rs.getLong(10)
                        , rs.getInt(11)
                        , ImageType.of(rs.getString(12))
                        , rs.getInt(13)
                        , rs.getInt(14)
                ),
                new Period(
                        toLocalDate(rs.getTimestamp(2))
                        , toLocalDate(rs.getTimestamp(3))
                ),
                new SessionType(
                        PriceType.of(rs.getString(4))
                        , new MaximumEnrollment(rs.getInt(5))
                        , rs.getInt(6)
                ),
                SessionStatus.of(rs.getString(7)),
                new Course(rs.getLong(15)
                        , rs.getString(16)
                        , rs.getLong(17)
                        , toLocalDateTime(rs.getTimestamp(18))
                        , toLocalDateTime(rs.getTimestamp(19))),
                toLocalDateTime(rs.getTimestamp(8)),
                toLocalDateTime(rs.getTimestamp(9))
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

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }

    private LocalDate toLocalDate(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime().toLocalDate();
    }
}
