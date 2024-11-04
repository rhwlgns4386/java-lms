package nextstep.courses.infrastructure.session;

import nextstep.courses.entity.CoverImageEntity;
import nextstep.courses.entity.SessionEntity;
import nextstep.courses.infrastructure.cover.CoverImageRepository;
import nextstep.courses.infrastructure.enrollment.StudentRepository;
import nextstep.courses.type.RecruitState;
import nextstep.courses.type.SessionState;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {

    private final static SessionEntityRowMapper SESSION_ENTITY_ROW_MAPPER = new SessionEntityRowMapper();

    private final JdbcOperations jdbcTemplate;
    private final CoverImageRepository coverImageRepository;
    private final StudentRepository studentRepository;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate, CoverImageRepository coverImageRepository,
                                 StudentRepository studentRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.coverImageRepository = coverImageRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public SessionEntity findById(Long sessionId) {
        String sql = "select "
                + "id, session_state, recruit_state, cover_file_path, session_fee, enrollment, max_enrollment, start_date, end_date "
                + "from session s "
                + "where id = ?";

        SessionEntity sessionEntity = jdbcTemplate.queryForObject(sql, SESSION_ENTITY_ROW_MAPPER, sessionId);
        List<String> coverFilePaths = coverImageRepository.findBySessionId(sessionId)
                .stream()
                .map(CoverImageEntity::getFilePath)
                .collect(Collectors.toList());

        if (sessionEntity != null) {
            sessionEntity.addCover(coverFilePaths);
            sessionEntity.addStudentEntities(studentRepository.findBySessionId(sessionId));
        }
        return sessionEntity;
    }

    @Override
    public int save(SessionEntity session, long courseId) {
        if (!session.isPersisted()) {
            return insert(session, courseId);
        }
        return update(session);
    }

    private int insert(SessionEntity session, long courseId) {
        String sql = "insert into session "
                + "(course_id, session_state, recruit_state, cover_file_path, session_fee, max_enrollment, start_date, end_date) "
                + "values(?, ?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int result = jdbcTemplate.update(connection -> insert(connection, sql, courseId, session), keyHolder);

        long sessionId = Optional.ofNullable(keyHolder.getKey())
                .orElseThrow(() -> new DataRetrievalFailureException("강의 저장 실패"))
                .longValue();

        session.getCoverImageEntities().forEach(coverImageEntity ->
                coverImageRepository.save(coverImageEntity, sessionId));

        return result;
    }

    private PreparedStatement insert(Connection connection, String sql,
                                     Long courseId, SessionEntity sessionEntity) throws SQLException {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
            int i = 1;
            ps.setLong(i++, courseId);
            ps.setString(i++, sessionEntity.getSessionState().name());
            ps.setString(i++, sessionEntity.getRecruitState().name());
            ps.setString(i++, sessionEntity.getCoverFilePath());
            ps.setLong(i++, sessionEntity.getSessionFee());
            ps.setInt(i++, sessionEntity.getMaxEnrollment());
            ps.setTimestamp(i++, Timestamp.valueOf(sessionEntity.getStartDate()));
            ps.setTimestamp(i++, Timestamp.valueOf(sessionEntity.getEndDate()));
            return ps;
    }

    private int update(SessionEntity session) {
        String sql = "update session set enrollment = ? where id = ?";
        return jdbcTemplate.update(sql, session.getEnrollment(), session.getId());
    }

    @Override
    public List<SessionEntity> findByCourseId(long courseId) {
        String sql = "select "
                + "id, session_state, recruit_state, cover_file_path, session_fee, enrollment, max_enrollment, start_date, end_date "
                + "from session "
                + "where course_id = ?";
        return jdbcTemplate.query(sql, SESSION_ENTITY_ROW_MAPPER, courseId);
    }

    private static class SessionEntityRowMapper implements RowMapper<SessionEntity> {

        @Override
        public SessionEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new SessionEntity(
                    rs.getLong("id"),
                    rs.getString("cover_file_path"),
                    SessionState.valueOf(rs.getString("session_state")),
                    RecruitState.valueOf(rs.getString("recruit_state")),
                    rs.getInt("enrollment"),
                    rs.getInt("max_enrollment"),
                    rs.getLong("session_fee"),
                    rs.getTimestamp("start_date"),
                    rs.getTimestamp("end_date"));
        }
    }
}
