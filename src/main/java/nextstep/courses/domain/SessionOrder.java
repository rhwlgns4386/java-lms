package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

public class SessionOrder {
   //private final Session session; //안에있는 sessionId, students 생각 안하고 아래처럼 가져와도될까

    private long orderId;
    private final long sessionId;
    private final NsUser student;
    private final OrderStateCode orderStateCode;
    private final SessionPrice salePrice;
    private final Instructor apprInstructor;

    public SessionOrder(long orderId, long sessionId, NsUser student, OrderStateCode orderStateCode, SessionPrice salePrice, Instructor apprInstructor) {
        this.orderId = orderId;
        this.sessionId = sessionId;
        this.student = student;
        this.orderStateCode = orderStateCode;
        this.salePrice = salePrice;
        this.apprInstructor = apprInstructor;
    }

    public long getOrderId(){
        return  orderId;
    }

    public long getSessionId() {
        return sessionId;
    }

    public long getStudentId() {
        return student.getId();
    }

    public long getOrderStateCode(){
        return orderStateCode.getOrderStateCode();
    }

    public NsUser getStudent(){
        return student;
    }

    public boolean isReadyOrderState() {
        return getOrderStateCode() == OrderStateCode.READY.getOrderStateCode();
    }

    //새롭게 객체 생성 이렇게 하는게 맞을까요?
    public SessionOrder createApproveSessionOrder(Instructor apprInstructor) {
        return new SessionOrder(orderId, sessionId,  student,  OrderStateCode.APPROVE, salePrice, apprInstructor);
    }

    public long getApprId() {
        return apprInstructor.getId();
    }

    public SessionOrder createCancelSessionOrder(Instructor cancelInstructor) {
        return new SessionOrder(orderId, sessionId,  student,  OrderStateCode.CANCEL, salePrice, cancelInstructor);
    }
}
