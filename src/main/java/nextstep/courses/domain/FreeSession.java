package nextstep.courses.domain;

import nextstep.courses.exception.CannotRegisteSessionException;
import nextstep.courses.request.RequestOrderParam;
import nextstep.courses.strategy.SessionStrategy;
import nextstep.users.domain.NsUser;

public class FreeSession extends Session implements SessionStrategy {
    // private Session session;//구성이 필요한 관계는 이렇게 합성

    public FreeSession(SessionId sessionId, SessionInfo sessionInfo, SessionImages sessionImages) {
        super(sessionId, sessionInfo, sessionImages, new SessionPrice(0), SessionType.FREE);
    }

    public void validateOrderSession(RequestOrderParam requestOrderParam) throws CannotRegisteSessionException {
        validateOrderSessionStatus();
        validateOrderSessionProgressCode();
        validateDuplicateStudent(requestOrderParam.getStudent());
    }

    private void validateDuplicateStudent(NsUser student) throws CannotRegisteSessionException {
        if (isDuplicateStudent(student)) {
            throw new CannotRegisteSessionException("강의는 중복 신청할 수 없습니다.");
        }
    }

    @Override
    public void orderSession(RequestOrderParam requestOrderParam) throws CannotRegisteSessionException {
        validateOrderSession(requestOrderParam);
        updateStudent(requestOrderParam.getStudent());
    }

}
