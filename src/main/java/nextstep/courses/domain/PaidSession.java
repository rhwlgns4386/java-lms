package nextstep.courses.domain;

import nextstep.courses.CannotRegisteSessionException;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class PaidSession extends Session implements SessionStrategy {
    //private Session session;

    private int studentMaxCount;

    public PaidSession(String title, LocalDateTime applyStartDate, LocalDateTime applyEndDate,
                       long salePrice, StateCode stateCode, int createId,
                       int fileSize, String type, int width, int height, String fileName, int studentMaxCount) {
        super(title, applyStartDate, applyEndDate, salePrice, stateCode, createId, fileSize, type, width, height, fileName, true);
        this.studentMaxCount = studentMaxCount;
    }

    @Override
    public void validateOrderSession(RequestOrderParam requestOrderParam) throws CannotRegisteSessionException {
        validateSalePrice(requestOrderParam.getPayment());
        validateMaxStudentCount();
        validateDuplicateStudent(requestOrderParam.getStudent());
        validateOrderSessionStatus();
    }

    private void validateDuplicateStudent(NsUser student) throws CannotRegisteSessionException {
        if (isDuplicateStudent(student)) {
            throw new CannotRegisteSessionException("강의는 중복 신청할 수 없습니다.");
        }
    }

    private void validateMaxStudentCount() throws CannotRegisteSessionException {
        if (studentMaxCount <= getStudentsSize()) {
            throw new CannotRegisteSessionException("최대인원 수를 초과하였습니다.");
        }
    }

    private void validateSalePrice(Payment payment) throws CannotRegisteSessionException {
        if (payment.getAmount() != getSalePrice()) {
            throw new CannotRegisteSessionException("결제한 금액과 수강료가 일치하지 않습니다.");
        }
    }

    public void orderSession(RequestOrderParam requestOrderParam) throws CannotRegisteSessionException {
        validateOrderSession(requestOrderParam);
        updateStudent(requestOrderParam.getStudent());
    }

    public int getStudentSize() {
        return getStudentsSize();
    }

    public int getStudentMaxCount() {
        return studentMaxCount;
    }

}
