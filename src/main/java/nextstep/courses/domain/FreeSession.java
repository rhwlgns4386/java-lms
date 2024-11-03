package nextstep.courses.domain;

import nextstep.courses.CannotRegisteSessionException;

public class FreeSession extends Session implements SessionStrategy {
    // private Session session;//구성이 필요한 관계는 이렇게 합성

    public FreeSession(SessionInfo sessionInfo,SessionImage sessionImage,
                       long salePrice, StateCode stateCode) {
        super(sessionInfo, sessionImage, 0, stateCode);
    }

    public void validateOrderSession(RequestOrderParam requestOrderParam) {
        validateOrderSessionStatus();
    }

    @Override
    public void orderSession(RequestOrderParam requestOrderParam) throws CannotRegisteSessionException {
        validateOrderSession(requestOrderParam);
        updateStudent(requestOrderParam.getStudent());
    }

}
