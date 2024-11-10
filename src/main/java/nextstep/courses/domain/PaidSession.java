package nextstep.courses.domain;

import nextstep.courses.exception.CannotRegisteSessionException;
import nextstep.courses.request.RequestOrderParam;
import nextstep.courses.strategy.SessionStrategy;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class PaidSession extends Session implements SessionStrategy {
    //private Session session;

    private int studentMaxCount;

    public PaidSession(SessionId sessionId, SessionInfo sessionInfo, SessionImages sessionImages, SessionPrice sessionPrice, int studentMaxCount) {
        super(sessionId, sessionInfo, sessionImages, sessionPrice, SessionType.PAID);
        this.studentMaxCount = studentMaxCount;
        if(sessionPrice.isLessThanOrEqualTo(0) ){
            throw new IllegalArgumentException("유료강의는 0원 이하가 될 수 없습니다.");
        }
    }

    public void validateOrderSession(RequestOrderParam requestOrderParam) throws CannotRegisteSessionException {
        validateOrderSessionStatus();
        validateOrderSessionProgressCode();
        validateSalePrice(requestOrderParam.getPayment());
        validateMaxStudentCount();
        validateDuplicateStudent(requestOrderParam.getStudent());
    }

    private void validateDuplicateStudent(NsUser student) throws CannotRegisteSessionException {
        if (isDuplicateStudent(student)) {
            throw new CannotRegisteSessionException("강의는 중복 신청할 수 없습니다.");
        }
    }

    private void validateMaxStudentCount() throws CannotRegisteSessionException {
        if (isMaxStudentCount()) {
            throw new CannotRegisteSessionException("최대인원 수를 초과하였습니다.");
        }
    }

    private boolean isMaxStudentCount() {
        return studentMaxCount <= getStudentsSize();
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
