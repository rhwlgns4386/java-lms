package nextstep.courses.infrastructure;

import nextstep.courses.domain.Instructor;
import nextstep.courses.domain.InstructorId;
import nextstep.courses.domain.OrderStateCode;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionOrder;
import nextstep.courses.domain.SessionPrice;
import nextstep.courses.domain.Students;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class SessionOrderRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionRepositoryTest.class);

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private SessionOrderRepository sessionOrderRepository;
    private SessionRepository sessionRepository;
    @BeforeEach
    void setUp() {
        sessionOrderRepository = new JdbcSessionOrderRepository(jdbcTemplate);
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("강의주문을 조회하고 강의주문을 한 후 주문정보를 조회")
    void sessionOrderCRUD() {
        Session session = sessionRepository.findSessionInfoById(4L);
        int count = sessionOrderRepository.saveOrderSession(NsUserTest.JAVAJIGI, session);

        assertThat(count).isEqualTo(1);

        sessionOrderRepository.saveOrderSession(NsUserTest.SANJIGI, session);
        sessionOrderRepository.saveOrderSession(new NsUser(3L), session);

        Students students = sessionOrderRepository.findOrderInfoBySessionId(4L);

        assertThat(students.getStudents()).hasSize(3);
        assertThat(students.getStudents()).contains(new NsUser(1L), new NsUser(2L), new NsUser(3L));

        LOGGER.debug("=====================");
        LOGGER.debug("Session: {}", session);
        LOGGER.debug("=====================");
        LOGGER.debug("students: {}", students);
    }

    @Test
    @DisplayName("1번주문번호 1번강의 1번유저 대기중 주문정보와" +
            "4번주문번호 2번강의 3번유저를 5번강사가 승인거절한 주문정보 조회")
    void findSessionOrderByOrderId() {
        SessionOrder sessionOrder = sessionOrderRepository.findSessionOrderByOrderId(1L);
        assertThat(sessionOrder.getSessionId()).isEqualTo(1);
        assertThat(sessionOrder.getStudentId()).isEqualTo(1);
        assertThat(sessionOrder.getOrderStateCode()).isEqualTo(OrderStateCode.READY.getOrderStateCode());
        assertThat(sessionOrder.getApprId()).isEqualTo(0);

        SessionOrder sessionOrder2 = sessionOrderRepository.findSessionOrderByOrderId(4L);
        assertThat(sessionOrder2.getSessionId()).isEqualTo(2);
        assertThat(sessionOrder2.getStudentId()).isEqualTo(3);
        assertThat(sessionOrder2.getOrderStateCode()).isEqualTo(OrderStateCode.CANCEL.getOrderStateCode());
        assertThat(sessionOrder2.getApprId()).isEqualTo(5);
    }

    @Test
    @DisplayName("1번주문번호 1번강의 1번유저 대기중인 강의를 7번 강사는 신청을 수락")
    void saveOrderStateSessionOrder_approve() {
        SessionOrder sessionOrder = sessionOrderRepository.findSessionOrderByOrderId(1L);
        assertThat(sessionOrder.getOrderStateCode()).isEqualTo(OrderStateCode.READY.getOrderStateCode());

        SessionOrder approvedSessionOrder = new SessionOrder(1,1,new NsUser(1L), OrderStateCode.APPROVE, new SessionPrice(1000), new Instructor(new InstructorId(7)));
        sessionOrderRepository.saveOrderStateSessionOrder(approvedSessionOrder);

        SessionOrder resultOrder = sessionOrderRepository.findSessionOrderByOrderId(1L);
        assertThat(resultOrder.getOrderStateCode()).isEqualTo(OrderStateCode.APPROVE.getOrderStateCode());
        assertThat(resultOrder.getSessionId()).isEqualTo(1);
        assertThat(resultOrder.getStudentId()).isEqualTo(1);
        assertThat(resultOrder.getApprId()).isEqualTo(7);
    }

    @Test
    @DisplayName("1번주문번호 1번강의 1번유저 대기중인 강의를 7번 강사는 신청을 거절")
    void saveOrderStateSessionOrder_cancel() {
        SessionOrder sessionOrder = sessionOrderRepository.findSessionOrderByOrderId(1L);
        assertThat(sessionOrder.getOrderStateCode()).isEqualTo(OrderStateCode.READY.getOrderStateCode());

        SessionOrder approvedSessionOrder = new SessionOrder(1,1,new NsUser(1L), OrderStateCode.CANCEL, new SessionPrice(1000), new Instructor(new InstructorId(7)));
        sessionOrderRepository.saveOrderStateSessionOrder(approvedSessionOrder);

        SessionOrder resultOrder = sessionOrderRepository.findSessionOrderByOrderId(1L);
        assertThat(resultOrder.getOrderStateCode()).isEqualTo(OrderStateCode.CANCEL.getOrderStateCode());
        assertThat(resultOrder.getSessionId()).isEqualTo(1);
        assertThat(resultOrder.getStudentId()).isEqualTo(1);
        assertThat(resultOrder.getApprId()).isEqualTo(7);
    }

}
