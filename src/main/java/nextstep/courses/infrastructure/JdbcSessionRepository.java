package nextstep.courses.infrastructure;

import nextstep.courses.domain.cover.CoverImage;
import nextstep.courses.domain.cover.CoverImageRepository;
import nextstep.courses.domain.session.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {

    private JdbcOperations jdbcTemplate;
    private CoverImageRepository coverImageRepository;
    private SessionRegistrationRepository sessionRegistrationRepository;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate, SessionRegistrationRepository sessionRegistrationRepository, CoverImageRepository coverImageRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionRegistrationRepository = sessionRegistrationRepository;
        this.coverImageRepository = coverImageRepository;
    }

    @Override
    @Transactional
    public Long saveFreeSession(FreeSession session) {
        Long sessionId = saveSession(session);
        saveRegistrations(sessionId, session.getRegisteredStudentIds());
        return sessionId;
    }

    @Override
    @Transactional
    public Long savePaidSession(PaidSession session) {
        Long sessionId = saveSession(session);
        saveRegistrations(sessionId, session.getRegisteredStudentIds());
        return sessionId;
    }

    private Long saveSession(DefaultSession session) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO session (status, start_date, end_date, cover_image_id, session_type, course_fee, max_students) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            PreparedStatementMapper.mapParameters(ps, session);
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    private void saveRegistrations(Long sessionId, List<Long> userIds) {
        if (userIds.isEmpty()) {
            return;
        }
        sessionRegistrationRepository.saveRegistrations(sessionId, userIds);
    }

    @Override
    @Transactional(readOnly = true)
    public DefaultSession findById(Long id) {
        String sql = "SELECT * FROM session WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                String sessionType = rs.getString("session_type");
                Status status = Status.from(rs.getString("status"));
                Period period = new Period(rs.getDate("start_date").toLocalDate(), rs.getDate("end_date").toLocalDate());
                CoverImage coverImage = coverImageRepository.findById(rs.getLong("cover_image_id"));
                List<Long> registeredUserIds = sessionRegistrationRepository.findRegisteredUserIds(id);
                Capacity capacity = createCapacity(rs, registeredUserIds);

                if (SessionType.PAID.getCode().equals(sessionType)) {
                    Money courseFee = new Money(rs.getLong("course_fee"));
                    return new PaidSession(id, status, period, coverImage, courseFee, capacity);
                }

                return new FreeSession(id, status, period, coverImage, capacity);
            }, id);

        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("세션을 찾을 수 없습니다: " + id, e);
        }
    }

    private static Capacity createCapacity(ResultSet rs, List<Long> registeredUserIds) throws SQLException {
        int maxStudents = SessionType.PAID.getCode().equals(rs.getString("session_type"))
                ? rs.getInt("max_students")
                : Integer.MAX_VALUE;

        return new Capacity(new HashSet<>(registeredUserIds), maxStudents);
    }

}
