package nextstep.courses.domain;

import nextstep.courses.CannotRegisteSessionException;

import java.time.LocalDateTime;

public class FreeSession extends Session implements SessionStrategy {
    // private Session session;//구성이 필요한 관계는 이렇게 합성

    public FreeSession(String title, LocalDateTime applyStartDate, LocalDateTime applyEndDate,
                       long salePrice, StateCode stateCode, int createId,
                       int fileSize, String type, int width, int height, String fileName) {
        super(title, applyStartDate, applyEndDate, salePrice, stateCode, createId, fileSize, type, width, height, fileName, false);
    }

    @Override
    public void validateOrderSession(RequestOrderParam requestOrderParam) {
        validateOrderSessionStatus();
    }

    @Override
    public void orderSession(RequestOrderParam requestOrderParam) throws CannotRegisteSessionException {
        validateOrderSession(requestOrderParam);
        updateStudent(requestOrderParam.getStudent());
    }

}
