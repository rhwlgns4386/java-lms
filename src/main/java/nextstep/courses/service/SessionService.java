package nextstep.courses.service;

import nextstep.courses.domain.Instructor;
import nextstep.courses.domain.SessionImages;
import nextstep.courses.domain.SessionOrder;
import nextstep.courses.domain.SessionPrice;
import nextstep.courses.domain.Students;
import nextstep.courses.exception.CannotApproveSessionException;
import nextstep.courses.exception.CannotRegisteSessionException;
import nextstep.courses.infrastructure.SessionOrderRepository;
import nextstep.courses.infrastructure.SessionRepository;
import nextstep.courses.request.RequestOrderParam;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionFactory;
import nextstep.courses.domain.SessionInfo;
import nextstep.courses.domain.SessionType;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final SessionOrderRepository sessionOrderRepository;

    public SessionService(SessionRepository sessionRepository, SessionOrderRepository sessionOrderRepository) {
        this.sessionRepository = sessionRepository;
        this.sessionOrderRepository = sessionOrderRepository;
    }

    public void registerPaidSession(SessionInfo sessionInfo, SessionImages sessionImages, SessionPrice salePrice, int studentMaxCount, SessionType sessionType) {
        Session session = SessionFactory.createSession(sessionInfo, sessionImages, salePrice, studentMaxCount, sessionType);

        sessionRepository.saveRegisterSession(session);
    }

    public Session findSessionInfoById(long sessionId) {
        return sessionRepository.findSessionInfoById(sessionId);
    }

    public Session orderSession(Payment payment, NsUser user, long sessionId) throws CannotRegisteSessionException {
        Session session = findSessionInfoById(sessionId);

        Students students = sessionOrderRepository.findOrderInfoBySessionId(sessionId);
        session.addStudents(students);

        RequestOrderParam param = new RequestOrderParam(payment, user);
        session.orderSession(param);

        sessionOrderRepository.saveOrderSession(user, session);
        return session;
    }

    public SessionOrder approveSessionOrder(Instructor instructor, long orderId) throws CannotApproveSessionException {
        SessionOrder sessionOrder = findSessionOrderByOrderId(orderId);

        SessionOrder approvedSessionOrder = instructor.approveSessionOrder(sessionOrder);

        sessionOrderRepository.saveOrderStateSessionOrder(approvedSessionOrder);

        return approvedSessionOrder;
    }

    public SessionOrder cancelSessionOrder(Instructor instructor, long orderId) throws CannotApproveSessionException {
        SessionOrder sessionOrder = findSessionOrderByOrderId(orderId);

        SessionOrder approvedSessionOrder = instructor.cancelSessionOrder(sessionOrder);

        sessionOrderRepository.saveOrderStateSessionOrder(approvedSessionOrder);

        return approvedSessionOrder;
    }

    public SessionOrder findSessionOrderByOrderId(long orerId){
        return sessionOrderRepository.findSessionOrderByOrderId(orerId);
    }

}
