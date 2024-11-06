package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionImages;
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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    //private static RowMapper<Session> session;

    public JdbcSessionRepository(NamedParameterJdbcTemplate jdbcTemplate) {
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
    public Session findSessionInfoById(long sessionId) {
        String sql = "SELECT s.title, s.apply_start_date, s.apply_end_date, s.sale_price, s.state_code, s.creator_id, " +
                "si.file_size, si.type, si.width, si.height, si.file_name, s.session_type, s.student_max_count, s.SESSION_ID, s.PROGRESS_CODE " +
                "FROM session s " +
                "LEFT JOIN session_image si ON s.SESSION_ID = si.SESSION_ID " +
                "WHERE s.SESSION_ID = :sessionId";
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", sessionId); // :sessionId에 값을 바인딩

        return jdbcTemplate.queryForObject(sql, params, (rs, rownum) -> {
            String title = rs.getString(1);
            LocalDateTime applyStartDate = toLocalDateTime(rs.getTimestamp(2));
            LocalDateTime applyEndDate = toLocalDateTime(rs.getTimestamp(3));
            long salePrice = rs.getLong(4);
            StateCode stateCode = StateCode.fromCode(rs.getLong(5));
            String creatorId = rs.getString(6);
            SessionType sessionType = SessionType.fromCode(rs.getString(12));
            int studentMaxCount = rs.getInt(13);
            long sessionIdFromDb = rs.getLong(14);
            ProgressCode progressCode = ProgressCode.fromCode(rs.getInt(15));

            List<SessionImage> sessionImages = new ArrayList<>();
            getSessionImages(rs, sessionImages);

            SessionMetaData sessionMetaData = new SessionMetaData(title, creatorId);
            SessionPeriod sessionPeriod = new SessionPeriod(applyStartDate, applyEndDate);

            return SessionFactory.createSession(new SessionInfo(sessionIdFromDb, sessionMetaData, sessionPeriod, stateCode, progressCode),
                    new SessionImages(sessionImages),
                    new SessionPrice(salePrice),
                    studentMaxCount,
                    sessionType);
        });
    }

    private static void getSessionImages(ResultSet rs, List<SessionImage> sessionImages) throws SQLException {
        do {
            int fileSize = rs.getInt(7);
            String type = rs.getString(8);
            int width = rs.getInt(9);
            int height = rs.getInt(10);
            String fileName = rs.getString(11);
            sessionImages.add(new SessionImage(fileSize, type, width, height, fileName));
        } while (rs.next());
    }

    @Override
    public Students findOrderInfoBySessionId(long sessionId) {
        String sql = "SELECT so.ns_user_id, so.session_id " +
                "FROM session s " +
                "LEFT JOIN session_order so ON s.session_id = so.session_id " +
                "WHERE s.session_id = :sessionId";

        RowMapper<NsUser> sessionOrderRowMapper = (rs, rownum) -> {
            long nsUserId = rs.getLong(1);
            return new NsUser(nsUserId);
        };
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", sessionId);

        List<NsUser> students = jdbcTemplate.query(sql, params, (rs, rownum) -> {
            long nsUserId = rs.getLong(1);  // 첫 번째 컬럼 ns_user_id
            return new NsUser(nsUserId);    // NsUser 객체로 변환
        });


        return new Students(students);
    }

    @Override
    public int saveOrderSession(NsUser user, Session session) {
        String sql = "insert into SESSION_ORDER(session_id, ns_user_id, sale_price) values (:sessionId, :userId, :salePrice)";
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", session.getSessionId());
        params.put("userId", user.getId());
        params.put("salePrice", session.getSalePrice());
        return jdbcTemplate.update(sql, params);
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
