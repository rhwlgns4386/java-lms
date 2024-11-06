package nextstep.courses.strategy;

import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.Session;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Timestamp;

public class PaidSessionRepositoryStrategy implements SessionRepositoryStrategy {

    //private final JdbcOperations jdbcTemplate;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PaidSessionRepositoryStrategy(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int saveRegisterSession(Session session) {
        String sql = "insert into session (title, apply_start_date, apply_end_date, sale_price, state_code, creator_id, session_type, student_max_count, PROGRESS_CODE) " +
                "values(:title, :apply_start_date, :apply_end_date, :sale_price," +
                        ":state_code, :creator_id, :session_type ,:student_max_count ,:progress_code)";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("title", session.getTitle())
                .addValue("apply_start_date", Timestamp.valueOf(session.getApplyStartDate()))
                .addValue("apply_end_date", Timestamp.valueOf(session.getApplyEndDate()))
                .addValue("sale_price", session.getSalePrice())
                .addValue("state_code", session.getStateCode())
                .addValue("creator_id", session.getCreatorId())
                .addValue("session_type", session.getSessionTypeCode())
                .addValue("student_max_count", ((PaidSession) session).getStudentMaxCount())
                .addValue("progress_code", session.getProgressCode());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);

        return saveRegisterSessionImages(session, keyHolder.getKey());
    }

    private int saveRegisterSessionImages(Session session, Number key) {
        String sql = ("insert into SESSION_IMAGE (session_id, file_name, file_size, type, width, height) " +
                "values(:session_id, :file_name, :file_size, :type, :width, :height)");

        session.getSessionImages().stream().forEach(
                (sessionImage) -> {
                    SqlParameterSource param = new MapSqlParameterSource()
                            .addValue("session_id", key.longValue())
                            .addValue("file_name", sessionImage.getFileName())
                            .addValue("file_size", sessionImage.getFileSize())
                            .addValue("type", sessionImage.getType())
                            .addValue("width", sessionImage.getWidth())
                            .addValue("height",  sessionImage.getHeight());

                    jdbcTemplate.update(sql, param);
                }
        );
        return session.getSessionImages().size();
    }

}
