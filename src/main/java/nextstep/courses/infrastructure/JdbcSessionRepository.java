package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionMetaData;
import nextstep.courses.domain.SessionPeriod;
import nextstep.courses.domain.SessionPrice;
import nextstep.courses.domain.Students;
import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.ProgressCode;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionImage;
import nextstep.courses.domain.SessionInfo;
import nextstep.courses.domain.SessionType;
import nextstep.courses.domain.StateCode;
import nextstep.courses.domain.SessionFactory;
import nextstep.courses.strategy.FreeSessionRepositoryStrategy;
import nextstep.courses.strategy.PaidSessionRepositoryStrategy;
import nextstep.courses.strategy.SessionRepositoryStrategy;
import nextstep.users.domain.NsUser;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    private final JdbcOperations jdbcTemplate;
    private static RowMapper<Session> session;
    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int saveRegisterSession(Session session) {
        SessionRepositoryStrategy sessionRepositoryStrategy = getSessionSaveRepository(session);
        return sessionRepositoryStrategy.saveRegisterSession(session);
    }

    private SessionRepositoryStrategy getSessionSaveRepository(Session session) {
        if (session instanceof PaidSession) {
            return new PaidSessionRepositoryStrategy(jdbcTemplate);
        }
        return new FreeSessionRepositoryStrategy(jdbcTemplate);
    }

    @Override
    public Session findSessionInfoById(long id) {
        String sql = "SELECT s.title, s.apply_start_date, s.apply_end_date, s.sale_price, s.state_code, s.creator_id, " +
                "si.file_size, si.type, si.width, si.height, si.file_name, s.session_type, s.student_max_count, s.SESSION_ID, s.PROGRESS_CODE " +
                "FROM session s " +
                "LEFT JOIN session_image si ON s.SESSION_ID = si.IMAGE_ID " +
                "WHERE s.SESSION_ID = ?";

        session = (rs, rownum) -> {
            String title = rs.getString(1);
            LocalDateTime applyStartDate = toLocalDateTime(rs.getTimestamp(2));
            LocalDateTime applyEndDate = toLocalDateTime(rs.getTimestamp(3));
            long salePrice = rs.getLong(4);
            StateCode stateCode = StateCode.fromCode(rs.getLong(5));
            String creatorId = rs.getString(6);
            SessionType sessionType = SessionType.fromCode(rs.getString(12));
            // 이미지 정보
            int fileSize = rs.getInt(7);
            String type = rs.getString(8);
            int width = rs.getInt(9);
            int height = rs.getInt(10);
            String fileName = rs.getString(11);
            int studentMaxCount = rs.getInt(13);
            long sessionId = rs.getLong(14);

            SessionMetaData sessionMetaData = new SessionMetaData(title, creatorId);
            SessionPeriod sessionPeriod = new SessionPeriod(applyStartDate, applyEndDate);
            SessionPrice sessionPrice = new SessionPrice(salePrice);
            SessionInfo sessionInfo = new SessionInfo(sessionId, sessionMetaData, sessionPeriod, stateCode);
            SessionImage sessionImage = new SessionImage(fileSize, type, width, height, fileName);

            return SessionFactory.createSession(sessionInfo, sessionImage, sessionPrice, studentMaxCount, sessionType);
        };
        return jdbcTemplate.queryForObject(sql, session, id);
    }

    @Override
    public Students findOrderInfoBySessionId(long id) {
        String sql = "SELECT so.ns_user_id, so.session_id " +
                "FROM session s " +
                "LEFT JOIN session_order so ON s.session_id = so.session_id " +
                "WHERE s.session_id = ?";

        List<NsUser> students = new ArrayList<>();

        RowMapper<NsUser> sessionOrderRowMapper = (rs, rownum) -> {
            long nsUserId = rs.getLong(1);
            return new NsUser(nsUserId);
        };
        students = jdbcTemplate.query(sql, sessionOrderRowMapper, id);

        return new Students(students);
    }

    @Override
    public int saveOrderSession(NsUser user, Session session) {
        String sql = "insert into SESSION_ORDER(session_id, ns_user_id, sale_price) values (?, ?, ?)";
        return jdbcTemplate.update(sql, session.getSessionId(), user.getId(), session.getSalePrice());
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
