package nextstep.courses.domain;

import nextstep.courses.CannotRegisteSessionException;

import java.time.LocalDateTime;

public class FreeSession extends Session implements SessionStrategy {
    // private Session session;//구성이 필요한 관계는 이렇게 합성

    public FreeSession(SessionInfo sessionInfo,SessionImage sessionImage,
                       long salePrice, StateCode stateCode) {
        super(sessionInfo, sessionImage, salePrice, stateCode);
        validateRegistSession(salePrice);
    }

    private void validateRegistSession(long salePrice) {
        if(salePrice > 0) {
            throw new IllegalArgumentException("무료강의는 판매가가 0원이어야 합니다.");
        }
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
