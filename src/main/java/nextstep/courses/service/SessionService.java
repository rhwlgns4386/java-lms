package nextstep.courses.service;

import nextstep.courses.collection.Students;
import nextstep.courses.exception.CannotRegisteSessionException;
import nextstep.courses.infrastructure.SessionRepository;
import nextstep.courses.request.RequestOrderParam;
import nextstep.courses.domain.Session;
import nextstep.courses.factory.SessionFactory;
import nextstep.courses.domain.SessionImage;
import nextstep.courses.domain.SessionInfo;
import nextstep.courses.domain.SessionType;
import nextstep.courses.collection.Sessions;
import nextstep.courses.domain.StateCode;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void registerPaidSession(SessionInfo sessionInfo, SessionImage sessionImage, long salePrice, StateCode stateCode, int studentMaxCount, SessionType sessionType) {
        Session session = SessionFactory.createSession(sessionInfo, sessionImage, salePrice, stateCode, studentMaxCount, sessionType);

        sessionRepository.saveRegisterSession(session);
    }

    public Session findSessionInfoById(long idx) {
        return sessionRepository.findSessionInfoById(idx);
    }

    public Session orderSession(Payment payment, NsUser user, long idx) throws CannotRegisteSessionException {
        Session session = findSessionInfoById(idx);

        Students students = sessionRepository.findOrderInfoBySessionId(idx);
        session.addStudents(students);

        RequestOrderParam param = new RequestOrderParam(payment, user);
        session.orderSession(param);

        sessionRepository.saveOrderSession(user, session);
        return session;
    }

}
