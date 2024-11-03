package nextstep.users.infrastructure;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import nextstep.users.domain.NsTeacher;
import nextstep.users.domain.TeacherRepository;

@Repository("teacherRepository")
public class JdbcTeacherRepository implements TeacherRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcTeacherRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(NsTeacher teacher) {
        String sql = "insert into ns_teacher (name) values (?)";

        return jdbcTemplate.update(sql,
            teacher.getName()
        );
    }

    @Override
    public Optional<NsTeacher> findById(Long id) {
        String sql = "select id, name from ns_teacher where id = ?";
        RowMapper<NsTeacher> rowMapper = (rs, rowNum) -> new NsTeacher(rs.getLong(1), rs.getString(2));

        return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }
}
