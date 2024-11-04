package nextstep.courses.infrastructure;

import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionImage;
import nextstep.courses.entity.SessionEntity;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

import static nextstep.courses.domain.EnrollStatus.APPLY;
import static nextstep.courses.domain.EnrollStatus.APPROVED;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    private final JdbcOperations jdbcTemplate;
    private final SessionImageRepository sessionImageRepository;
    private final SessionStudentRepository sessionStudentRepository;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate, SessionImageRepository sessionImageRepository, SessionStudentRepository sessionStudentRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionImageRepository = sessionImageRepository;
        this.sessionStudentRepository = sessionStudentRepository;
    }

    @Override
    public int save(Session session) {
        SessionEntity entity = SessionEntity.from(session);
        String sql = "insert into session (session_start_date, session_end_date, status, image_id, session_type, max_student, session_fee) values (? ,? ,? ,? ,? ,? ,?)";
        sessionStudentRepository.save(session.getSessionId(), session.getApprovedStudents());
        return jdbcTemplate.update(sql, entity.getSessionStartAt(), entity.getSessionEndAt(), entity.getStatus(), entity.getImageId(), entity.getSessionType(), entity.getSessionFee(), entity.getMaxStudent());
    }

    @Override
    public int saveNew(Session session) {
        SessionEntity entity = SessionEntity.from(session);
        String sql = "insert into session (session_start_date, session_end_date, status, recruiting_status, progress_status, image_id, session_type, max_student, session_fee, session_selection) values (? ,? ,? ,? ,? ,? ,?, ?, ?, ?)";
        sessionStudentRepository.saveNew(session.getSessionId(), session.getApprovedStudents(), session.getApplyStudents());
        return jdbcTemplate.update(sql, entity.getSessionStartAt(), entity.getSessionEndAt(), entity.getStatus(), entity.getRecruitingStatus(), entity.getProgressStatus(), entity.getImageId(), entity.getSessionType(), entity.getSessionFee(), entity.getMaxStudent(), entity.getSessionSelection());
    }

    @Override
    public Session findById(Long id) {
        String sql = "select id, session_start_date, session_end_date, status, image_id, session_type, max_student, session_fee from session where id = ?";
        List<Long> students = sessionStudentRepository.findBySessionId(id);
        RowMapper<Session> rowMapper = (rs, rowNum) -> {
            SessionImage sessionImage = getSessionImage(rs.getLong("image_id"));
            return new SessionEntity(rs.getLong("id"),
                    rs.getTimestamp("session_start_date"),
                    rs.getTimestamp("session_end_date"),
                    rs.getString("status"),
                    rs.getString("session_type"),
                    rs.getInt("max_student"),
                    rs.getInt("session_fee"))
                    .to(sessionImage, students);
        };
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public Session findByIdNew(Long id) {
        String sql = "select id, session_start_date, session_end_date, status, recruiting_status, progress_status, image_id, session_type, max_student, session_fee, session_selection from session where id = ?";
        List<Long> approvedStudents = sessionStudentRepository.findBySessionIdAndStatus(id, APPROVED);
        List<Long> applyStudents = sessionStudentRepository.findBySessionIdAndStatus(id, APPLY);
        RowMapper<Session> rowMapper = (rs, rowNum) -> {
            List<SessionImage> sessionImages = getSessionImages(id);
            return new SessionEntity(rs.getLong("id"),
                    rs.getTimestamp("session_start_date"),
                    rs.getTimestamp("session_end_date"),
                    rs.getString("status"),
                    rs.getString("recruiting_status"),
                    rs.getString("progress_status"),
                    rs.getString("session_type"),
                    rs.getInt("max_student"),
                    rs.getInt("session_fee"),
                    rs.getString("session_selection"))
                    .toNew(sessionImages, approvedStudents, applyStudents);
        };
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    private SessionImage getSessionImage(Long imageId) {
        return sessionImageRepository.findById(imageId);
    }

    private List<SessionImage> getSessionImages(Long sessionId) {
        return sessionImageRepository.findBySessionId(sessionId);
    }

}
