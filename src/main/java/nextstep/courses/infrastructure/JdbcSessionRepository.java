package nextstep.courses.infrastructure;

import java.util.List;
import nextstep.courses.domain.Payments;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionDuration;
import nextstep.courses.domain.SessionInfo;
import nextstep.courses.domain.SessionRegisterInfo;
import nextstep.courses.domain.SessionRegisteringStatus;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.SessionStatus;
import nextstep.courses.domain.SessionType;
import nextstep.courses.domain.Student;
import nextstep.courses.domain.Students;
import nextstep.payments.domain.Payment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    private JdbcTemplate jdbcTemplate;
    public JdbcSessionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Session session) {
        SessionRegisterInfo sessionRegisterInfo = session.getSessionRegisterInfo();
        SessionInfo sessionInfo = session.getSessionInfo();
        SessionDuration sessionDuration = session.getSessionDuration();
        String sql = "insert into session (session_id,session_status,session_type, price, max_students, start_date, end_date,session_register_status) "
                + "values (?,?,?,?,?,?,?,?);";
        return jdbcTemplate.update(sql, session.getSessionId()
        ,sessionRegisterInfo.getSessionStatus().name(),sessionInfo.getSessionType().name(),sessionInfo.getPrice(),sessionInfo.getMaxStudents()
        ,sessionDuration.getStartDate(),sessionDuration.getEndDate(),session.getSessionRegisterInfo().getSessionRegisteringStatus().name());
    }

    @Override
    public Session findById(Long id) {
        SessionInfo sessionInfo = findByIdSessionInfo(id);
        SessionRegisterInfo sesssionRegisterInfo = findByIdSessionRegisterInfo(id);
        SessionDuration sessionDuration = findByIdSessionDuration(id);
        SessionRegisteringStatus sessionRegisteringStatus = findByIdSessionRegisteringStatus(id);

        String sql = "select * from session where session_id = ?";
        RowMapper<Session> rowMapper = ((rs, rowNum)
                -> Session.createPaidSession(rs.getLong(1)
                , null, sessionInfo.getSessionType(),sesssionRegisterInfo.getSessionStatus()
        ,sessionInfo.getPrice(),  sessionInfo.getMaxStudents(), sessionDuration, sessionRegisteringStatus));
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public SessionInfo findByIdSessionInfo(Long id) {
        String sql = "select session_id, session_type, price, max_students from session where session_id = ?";
        RowMapper<SessionInfo> rowMapper = ((rs, rowNum)
                -> new SessionInfo(rs.getLong("session_id")
                , SessionType.valueOf(rs.getString("session_type"))
                , rs.getLong("price")
                ,rs.getInt("max_students")));
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public SessionRegisterInfo findByIdSessionRegisterInfo(Long Id){
        String sql = "select session_id, session_status , session_register_status from session where session_id = ?;";
        String studentSql = "select * from students where session_id = ?;";
        String paymentSql = "select * from payments where session_id = ?;";
        RowMapper<Student> studentMapper = (rs, rowNum) -> Student.selectedStudent(rs.getString("user_id"),rs.getLong("session_id"));
        RowMapper<Payment> paymentsMapper = (rs, rowNum) ->
                new Payment(rs.getString("user_id"), rs.getLong("session_id"),
                        rs.getLong("amount"), rs.getLong("create_at"));
        List<Student> students = jdbcTemplate.query(studentSql, studentMapper, Id);
        List<Payment> payments = jdbcTemplate.query(paymentSql, paymentsMapper, Id);
        RowMapper<SessionRegisterInfo> rowMapper =(rs, rowNum) -> new SessionRegisterInfo(
                rs.getLong("session_id"), SessionStatus.valueOf(rs.getString("session_status")),
                Students.from(students), Payments.from(payments), SessionRegisteringStatus.valueOf(rs.getString("session_register_status")));
        return jdbcTemplate.queryForObject(sql, rowMapper, Id);
    }

    public SessionDuration findByIdSessionDuration(Long id) {
        String sql = "select session_id, start_date, end_date from session where session_id = ?";
        RowMapper<SessionDuration> rowMapper = ((rs, rowNum)
                -> new SessionDuration(rs.getLong("session_id")
                , rs.getTimestamp("start_date").toLocalDateTime()
                , rs.getTimestamp("end_date").toLocalDateTime()));
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public SessionRegisteringStatus findByIdSessionRegisteringStatus(Long id) {
        String sql = "select session_register_status from session where session_id = ?";
        RowMapper<SessionRegisteringStatus> rowMapper = ((rs, rowNum)
        -> SessionRegisteringStatus.valueOf(rs.getString("session_register_status")));
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

}
