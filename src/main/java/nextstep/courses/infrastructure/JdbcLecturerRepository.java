package nextstep.courses.infrastructure;

import nextstep.courses.domain.lecturer.Lecturer;
import nextstep.courses.domain.lecturer.LecturerRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JdbcLecturerRepository implements LecturerRepository {
    private static final RowMapper<Lecturer> LECTURER_ROW_MAPPER = (rs, rowNum) -> new Lecturer(rs.getLong(1));

    private NamedParameterJdbcOperations namedParameterJdbcTemplate;

    public JdbcLecturerRepository(NamedParameterJdbcOperations namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public int save(Lecturer lecturer, Long sessionId) {
        String sql = "insert into lecturer (ns_user_id, session_id) values(:nsUserId,:sessionId)";
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("nsUserId", lecturer.getNsUserId());
        param.addValue("sessionId", sessionId);
        return namedParameterJdbcTemplate.update(sql, param);
    }

    @Override
    public Optional<Lecturer> findByNsUserId(Long userId) {
        String sql = "select ns_user_id from lecturer where ns_user_id = :nsUserId";
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("nsUserId", userId);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, param, LECTURER_ROW_MAPPER));
    }

    @Override
    public Optional<Lecturer> findBySessionId(Long sessionId) {
        String sql = "select ns_user_id from lecturer where session_id = :sessionId";
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("sessionId", sessionId);
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, param, LECTURER_ROW_MAPPER));
    }
}
