package nextstep.courses.domain;

import nextstep.courses.domain.SessionImage.SessionImage;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class Session {
    private Students students;
    private Premium premium;
    private SessionState sessionState;
    private SessionImage sessionImage;

    private int maxStudentCount;
    private SessionDate sessionDate;




    public Session(Premium premium, int maxStudentCount, SessionState sessionState, SessionDate sessionDate
            ,SessionImage sessionImage) {
        this.premium = premium;
        this.students = new Students();
        this.maxStudentCount = maxStudentCount;
        this.sessionState = sessionState;
        this.sessionDate = sessionDate;
        this.sessionImage = sessionImage;

    }

    public void requestSession(Payment payment) {
        validate();
        validateSessionState();
        premium.validateAmount(payment);
        NsUser payingUser = payment.payingUser();
        students.addStudent(payingUser);

    }

    public void validate() {
        if (premium.isPremium() && students.studentCount() >= maxStudentCount) {
            throw new IllegalArgumentException("수강인원 초과");
        }
    }

    private void validateSessionState() {
        if (!SessionState.isRequestSession(this.sessionState)) {
            throw new IllegalArgumentException("강의신청 기간이 아닙니다.");
        }

    }

}
