package nextstep.courses.domain.session;

import nextstep.courses.domain.cover.CoverImage;
import nextstep.courses.domain.cover.CoverImages;
import nextstep.courses.domain.enrollment.Student;
import nextstep.courses.type.RecruitState;
import nextstep.courses.type.SelectionType;
import nextstep.courses.type.SessionState;
import nextstep.payments.domain.Payment;

import java.time.LocalDateTime;
import java.util.List;

public class PaidSession extends Session {

    private long sessionFee;

    protected PaidSession(Long id, CoverImage coverImage, CoverImages coverImages, SessionState sessionState,
                          RecruitState recruitState, SelectionType selectionType, int maxEnrollment, int enrollment,
                          long sessionFee, LocalDateTime startDate, LocalDateTime endDate, List<Student> students) {
        super(id, coverImage, coverImages, maxEnrollment, enrollment, sessionState, recruitState, selectionType, startDate,
                endDate, students);
        this.sessionFee = sessionFee;
    }

    @Override
    protected boolean isValidPayment(Payment payment) {
        return payment.isValidPayment(id, sessionFee);
    }

    @Override
    public long getSessionFee() {
        return sessionFee;
    }
}
