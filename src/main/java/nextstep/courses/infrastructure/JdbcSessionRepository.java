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
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import static java.sql.Date.*;

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
            ps.setString(1, session.getStatus().getCode());
            ps.setDate(2, valueOf(session.getStartDate()));
            ps.setDate(3, valueOf(session.getEndDate()));
            ps.setLong(4, session.getCoverImageId());
            ps.setString(5, session instanceof PaidSession ? SessionType.PAID.getCode() : SessionType.FREE.getCode());
            setNullableParameter(ps, 6, session instanceof PaidSession ? session.getCourseFee() : null);
            setNullableParameter(ps, 7, session.getCapacity().getMaxStudentsSize());
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

    private <T> void setNullableParameter(PreparedStatement ps, int index, T value) throws SQLException {
        if (value instanceof Integer) {
            ps.setInt(index, (Integer) value);
            return;
        }
        if (value instanceof Long) {
            ps.setLong(index, (Long) value);
            return;
        }
        if (value == null) {
            ps.setNull(index, Types.NULL);
            return;
        }
        throw new IllegalArgumentException("지원하지 않는 타입입니다: " + value.getClass().getName());
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

                Capacity capacity = new Capacity(new HashSet<>(registeredUserIds), SessionType.PAID.getCode().equals(sessionType) ? rs.getInt("max_students") : Integer.MAX_VALUE);
                Money courseFee = SessionType.PAID.getCode().equals(sessionType) ? new Money(rs.getLong("course_fee")) : new Money(0L);

                return SessionFactory.createSession(sessionType, id, status, period, coverImage, courseFee, capacity);
            }, id);

        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("세션을 찾을 수 없습니다: " + id, e);
        }
    }

}
