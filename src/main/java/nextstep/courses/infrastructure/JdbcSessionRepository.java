package nextstep.courses.infrastructure;

import nextstep.courses.domain.CoverImageRepository;
import nextstep.courses.domain.SessionRegistrationRepository;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.cover.CoverImage;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {

    private SessionRegistrationRepository sessionRegistrationRepository;
    private JdbcOperations jdbcTemplate;
    private CoverImageRepository coverImageRepository;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate, SessionRegistrationRepository sessionRegistrationRepository, CoverImageRepository coverImageRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionRegistrationRepository = sessionRegistrationRepository;
        this.coverImageRepository = coverImageRepository;
    }

    @Override
    @Transactional
    public Long saveFreeSession(FreeSession session) {
        SessionEntity entity = SessionEntity.fromFreeSession(session);
        Long sessionId = saveSessionEntity(entity);
        saveRegistrations(sessionId, entity.getRegisteredUserIds());
        return sessionId;
    }

    @Override
    @Transactional
    public Long savePaidSession(PaidSession session) {
        SessionEntity entity = SessionEntity.fromPaidSession(session);
        Long sessionId = saveSessionEntity(entity);
        saveRegistrations(sessionId, entity.getRegisteredUserIds());
        return sessionId;
    }

    private Long saveSessionEntity(SessionEntity entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO session (status, start_date, end_date, cover_image_id, " +
                            "session_type, course_fee, max_students) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    new String[]{"id"}
            );

            ps.setString(1, entity.getStatus());
            ps.setDate(2, java.sql.Date.valueOf(entity.getStartDate()));
            ps.setDate(3, java.sql.Date.valueOf(entity.getEndDate()));
            ps.setLong(4, entity.getCoverImageId());
            ps.setString(5, entity.getSessionType());
            setNullableLong(ps, 6, entity.getCourseFee());
            setNullableInt(ps, 7, entity.getMaxStudents());

            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    private void saveRegistrations(Long sessionId, List<Long> userIds) {
        if (userIds.isEmpty()) {
            return;
        }

        LocalDateTime registeredAt = LocalDateTime.now();
        List<SessionRegistrationEntity> registrations = userIds.stream()
                .map(userId -> new SessionRegistrationEntity(sessionId, userId, registeredAt))
                .collect(Collectors.toList());

        sessionRegistrationRepository.saveRegistrations(registrations);
    }


    private void setNullableLong(PreparedStatement ps, int index, Long value) throws SQLException {
        if (value != null) {
            ps.setLong(index, value);
        } else {
            ps.setNull(index, Types.BIGINT);
        }
    }

    private void setNullableInt(PreparedStatement ps, int index, Integer value) throws SQLException {
        if (value != null) {
            ps.setInt(index, value);
        } else {
            ps.setNull(index, Types.INTEGER);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DefaultSession findById(Long id) {
        String sql = "SELECT * FROM session WHERE id = ?";

        try {
            SessionEntity entity = jdbcTemplate.queryForObject(
                    sql,
                    (rs, rowNum) -> new SessionEntity(
                            rs.getLong("id"),
                            rs.getString("status"),
                            rs.getDate("start_date").toLocalDate(),
                            rs.getDate("end_date").toLocalDate(),
                            rs.getLong("cover_image_id"),
                            rs.getString("session_type"),
                            rs.getObject("course_fee") != null ? rs.getLong("course_fee") : null,
                            rs.getObject("max_students") != null ? rs.getInt("max_students") : null,
                            sessionRegistrationRepository.findRegisteredUserIds(id)
                    ),
                    id
            );

            if (entity == null) {
                throw new EmptyResultDataAccessException(1);
            }
            CoverImage coverImage = coverImageRepository.findById(entity.getCoverImageId());

            if (SessionType.PAID.getCode().equals(entity.getSessionType())) {
                return entity.toPaidSessionDomain(coverImage);
            }
            return entity.toFreeSessionDomain(coverImage);

        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("세션을 찾을 수 없습니다." + id);
        }
    }

}
