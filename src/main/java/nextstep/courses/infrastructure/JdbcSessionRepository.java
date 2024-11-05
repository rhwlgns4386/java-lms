package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.session.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Session session) {
        StringBuilder sb = new StringBuilder();

        sb.append("insert into course_session");
        sb.append(" (course_id, session_status, maximum_number_people, session_pay, session_pay_type, start_date, end_date, progress_status, recruit_status) ");
        sb.append("values");
        sb.append(" (?, ?, ?, ?, ?, ?, ?, ?, ?);");

        KeyHolder keyHolder = new GeneratedKeyHolder();

        SessionPay sessionPay = session.getSessionPay();
        SessionStudents students = session.getStudents();
        SessionPeriod period = session.getPeriod();
        SessionApplyStatus applyStatus = session.getApplyStatus();

        jdbcTemplate.update(connect -> {
            PreparedStatement ps = connect.prepareStatement(sb.toString(), new String[]{"id"});
            ps.setLong(1, session.getCourseId());
            ps.setString(2, session.getStatus().toString());
            ps.setInt(3, students.getMaximumNumberPeople());
            ps.setLong(4, sessionPay.getSessionPay());
            ps.setString(5, sessionPay.getSessionPayType().toString());
            ps.setTimestamp(6, toTimeStamp(period.getStartDate()));
            ps.setTimestamp(7, toTimeStamp(period.getEndDate()));
            ps.setString(8, applyStatus.getProgressStatus().toString());
            ps.setString(9, applyStatus.getRecruitStatus().toString());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public Session findById(Long id) {
        StringBuilder sb = new StringBuilder();

        sb.append("select ");
        sb.append(" id, course_id, session_status, session_pay, session_pay_type, ");
        sb.append(" start_date, end_date, maximum_number_people, progress_status, recruit_status ");
        sb.append("from course_session ");
        sb.append("where id = ? ");

        RowMapper<Session> rowMapper = ((rs, rowNum) -> new Session(
                rs.getLong(1),
                rs.getLong(2),
                rs.getString(3),
                new SessionPay(
                        rs.getLong(4),
                        rs.getString(5)
                ),
                new SessionPeriod(
                        toLocalDateTime(rs.getTimestamp(6)),
                        toLocalDateTime(rs.getTimestamp(7))
                ),
                rs.getInt(8),
                new SessionApplyStatus(
                        SessionProgressStatus.search(rs.getString(9)),
                        SessionRecruitStatus.search(rs.getString(10))
                )
        ));
        return jdbcTemplate.queryForObject(sb.toString(), rowMapper, id);
    }


    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }

    private Timestamp toTimeStamp(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }

        return Timestamp.valueOf(localDateTime);
    }
}
