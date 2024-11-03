package nextstep.courses.service;

import nextstep.courses.exception.CannotRegisteSessionException;
import nextstep.courses.request.RequestOrderParam;
import nextstep.courses.domain.Session;
import nextstep.courses.factory.SessionFactory;
import nextstep.courses.domain.SessionImage;
import nextstep.courses.domain.SessionInfo;
import nextstep.courses.domain.SessionType;
import nextstep.courses.collection.Sessions;
import nextstep.courses.domain.StateCode;
import nextstep.courses.infrastructure.SessionRepository;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class SessionService {

    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void registerPaidSession(SessionInfo sessionInfo, SessionImage sessionImage, long salePrice, StateCode stateCode, int studentMaxCount, SessionType sessionType) {

        Session session = SessionFactory.createSession(sessionInfo, sessionImage, salePrice, stateCode, studentMaxCount, sessionType);

        sessionRepository.insert(session);
    }

    public void orderPaidSession(Sessions sessions, Payment payment, NsUser user, int idx) throws CannotRegisteSessionException {
        //db에서 find 가져온다 가정, Sessions 파라미터 삭제 예정
        Session session = findSessionCreateId(idx, sessions);

        RequestOrderParam param = new RequestOrderParam(payment, user);
        session.orderSession(param);
        //update db student + 1
    }

    //추후 db로 변경 예정..
    private Session findSessionCreateId(int idx, Sessions sessions) {
        return sessions.getSessionIdx(idx);
    }

}
