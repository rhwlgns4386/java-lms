package nextstep.courses.infrastructure;

import nextstep.courses.domain.Instructor;
import nextstep.courses.domain.InstructorId;
import nextstep.courses.domain.OrderStateCode;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionOrder;
import nextstep.courses.domain.SessionPrice;
import nextstep.courses.domain.Students;
import nextstep.users.domain.NsUser;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("sessionOrderRepository")
public class JdbcSessionOrderRepository implements SessionOrderRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcSessionOrderRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Students findOrderInfoBySessionId(long sessionId) {
        String sql = "SELECT so.ns_user_id, so.session_id " +
                "FROM session s " +
                "LEFT JOIN session_order so ON s.session_id = so.session_id " +
                "WHERE s.session_id = :sessionId";

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
        String sql = "insert into SESSION_ORDER(session_id, ns_user_id, sale_price, ord_stat_code, APPR_ID) values (:sessionId, :userId, :salePrice, :orderStatCode, :apprId)";
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", session.getSessionId());
        params.put("userId", user.getId());
        params.put("salePrice", session.getSalePrice());
        params.put("orderStatCode", OrderStateCode.READY.getOrderStateCode());
        params.put("apprId", null);
        return jdbcTemplate.update(sql, params);
    }

    @Override
    public SessionOrder findSessionOrderByOrderId(long orderId) {
        String sql = "select ORDER_ID, SESSION_ID, NS_USER_ID, SALE_PRICE, ORD_STAT_CODE, APPR_ID " +
                "from SESSION_ORDER where ORDER_ID = :orderId";
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        return jdbcTemplate.queryForObject(sql, params, (rs, rownum) -> {
            long sessionId = rs.getLong(2);
            long studentId = rs.getLong(3);
            long salePrice = rs.getLong(4);
            int ordStatCode = rs.getInt(5);
            long apprId = rs.getLong(6);
            InstructorId instructorId = new InstructorId(apprId);
            return new SessionOrder(orderId, sessionId, new NsUser(studentId), OrderStateCode.fromCode(ordStatCode), new SessionPrice(salePrice), new Instructor(instructorId));
        });

    }

    @Override
    public int saveOrderStateSessionOrder(SessionOrder sessionOrder) {
        String sql = "update SESSION_ORDER set " +
                "ORD_STAT_CODE = :orderStatCode, " +
                "APPR_ID = :apprId " +
                "where ORDER_ID = :orderId";

        Map<String, Object> params = new HashMap<>();
        params.put("orderId", sessionOrder.getOrderId());
        params.put("orderStatCode", sessionOrder.getOrderStateCode());
        params.put("apprId", sessionOrder.getApprId());

        return jdbcTemplate.update(sql, params);

    }
}
