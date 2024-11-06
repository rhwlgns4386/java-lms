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
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        saveRegistrations(sessionId, session.getRegistrations());
        saveCoverImages(sessionId, session.getCoverImages());
        return sessionId;
    }

    @Override
    @Transactional
    public Long savePaidSession(PaidSession session) {
        Long sessionId = saveSession(session);
        saveRegistrations(sessionId, session.getRegistrations());
        saveCoverImages(sessionId, session.getCoverImages());
        return sessionId;
    }

    private Long saveSession(DefaultSession session) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO session (start_date, end_date, session_type, course_fee, max_students) " +
                "VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
            ps.setTimestamp(1, Timestamp.valueOf(session.getPeriod().getStartDate().atStartOfDay()));
            ps.setTimestamp(2, Timestamp.valueOf(session.getPeriod().getEndDate().atStartOfDay()));
            ps.setString(3, session.getTypeCode());
            ps.setLong(4, session.getCourseFeeAmount());
            ps.setInt(5, session.getMaxStudentsSize());
            return ps;
        }, keyHolder);

        long sessionId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        saveSessionStatus(sessionId, session.getStatus());
        return sessionId;

    }

    private void saveRegistrations(Long sessionId, List<SessionRegistration> users) {
        if (users.isEmpty()) {
            return;
        }
        List<Long> userIds = users.stream()
                .map(SessionRegistration::getUserId)
                .collect(Collectors.toUnmodifiableList());
        sessionRegistrationRepository.saveRegistrations(sessionId, userIds);
    }

    private void saveCoverImages(Long sessionId, List<CoverImage> images) {
        if (images.isEmpty()) {
            return;
        }

        for (CoverImage image : images) {
            jdbcTemplate.update(
                    "INSERT INTO session_cover_images (session_id, cover_image_id) VALUES (?, ?)",
                    sessionId, image.getId()
            );
        }
    }

    private void saveSessionStatus(Long sessionId, SessionStatus sessionStatus) {
        String sql = "INSERT INTO session_status (session_id, progress_status, recruitment_status) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, sessionId, sessionStatus.getProgressCode(), sessionStatus.getRecruitmentCode());
    }

    @Override
    @Transactional(readOnly = true)
    public DefaultSession findById(Long id) {
        String sql = "SELECT * FROM session WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                String sessionType = rs.getString("session_type");
                Period period = new Period(rs.getDate("start_date").toLocalDate(), rs.getDate("end_date").toLocalDate());
                Money courseFee = new Money(rs.getLong("course_fee"));
                int maxStudents = rs.getInt("max_students");
                SessionStatus sessionStatus = findSessionStatusById(id);
                List<CoverImage> images = coverImageRepository.findBySessionId(id);

                List<SessionRegistration> registrations = sessionRegistrationRepository.findRegisteredUsers(id);

                if (SessionType.PAID.getCode().equals(sessionType)) {
                    return new PaidSession(id, sessionStatus, period, images, courseFee, maxStudents, registrations);
                }
                return new FreeSession(id, sessionStatus, period, images, maxStudents, registrations);
            }, id);

        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("세션을 찾을 수 없습니다: " + id, e);
        }
    }

    private SessionStatus findSessionStatusById(Long sessionId) {
        String sql = "SELECT progress_status, recruitment_status FROM session_status WHERE session_id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new SessionStatus(
                SessionProgress.from(rs.getString("progress_status")),
                RecruitmentStatus.from(rs.getString("recruitment_status"))
        ), sessionId);
    }

}
