package nextstep.courses.infrastructure;

import nextstep.courses.domain.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class JdbcSessionRepository implements SessionRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Session session) {
        String sql = "insert into session(course_id, start_date, end_date, pricing_type, price, progress_status, recruit_status, available_seat, creator_id, created_at, updated_at) " +
                     "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(
                sql,
                session.getCourseId(),
                session.getDateRange().getStartDate(),
                session.getDateRange().getEndDate(),
                session.getPricing().getPricingType().name(),
                session.getPricing().getPrice(),
                session.getStatus().getProgressStatus().name(),
                session.getStatus().getRecruitmentStatus().name(),
                session.getAvailableSeats(),
                session.getCreatorId(),
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    @Override
    public Optional<Session> findById(Long id) {
        String sql = "select id, course_id, start_date, end_date, pricing_type, price, progress_status, recruit_status, available_seat, creator_id, created_at, updated_at from session where id = ?";

        RowMapper<Session> rowMapper = (rs, rowNum) -> new Session(
                rs.getLong("id"),
                rs.getLong("course_id"),
                new SessionPeriod(
                        toLocalDateTime(rs.getTimestamp("start_date")),
                        toLocalDateTime(rs.getTimestamp("end_date"))
                ),
                new Status(
                        ProgressStatus.valueOf(rs.getString("progress_status")),
                        RecruitmentStatus.valueOf(rs.getString("recruit_status"))
                ),
                null,
                new Pricing(
                        PricingType.valueOf(rs.getString("pricing_type")),
                        rs.getInt("price")
                ),
                null,
                rs.getInt("available_seat"),
                rs.getLong("creator_id"),
                toLocalDateTime(rs.getTimestamp("created_at")));

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
