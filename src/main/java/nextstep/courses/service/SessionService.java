package nextstep.courses.service;

import nextstep.courses.CannotRegisteSessionException;
import nextstep.courses.domain.RequestOrderParam;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionFactory;
import nextstep.courses.domain.Sessions;
import nextstep.courses.domain.StateCode;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class SessionService {

    public Sessions registerPaidSession() {
        Session paidSession = SessionFactory.createSession("제목1", LocalDateTime.now(), LocalDateTime.now().plus(10, ChronoUnit.HALF_DAYS),
                1000, StateCode.RECRUITING, 1, 100, "jpg", 300, 200, "imageFileName1", 2, true);

        Session freeSession = SessionFactory.createSession("제목2", LocalDateTime.now(), LocalDateTime.now().plus(10, ChronoUnit.HALF_DAYS),
                0, StateCode.RECRUITING, 2, 100, "jpg", 300, 200, "imageFileName1",false);

        //db insert 강의 등록으로 변경 예정
        List<Session> sessions = new ArrayList<>();
        sessions.add(paidSession);
        sessions.add(freeSession);
        return new Sessions(sessions);
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
