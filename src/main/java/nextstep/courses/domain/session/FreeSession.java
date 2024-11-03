package nextstep.courses.domain.session;

import nextstep.courses.domain.cover.CoverImage;
import nextstep.courses.domain.cover.CoverImages;
import nextstep.courses.domain.enrollment.Enrollment;
import nextstep.courses.domain.enrollment.Student;
import nextstep.courses.type.RecruitState;
import nextstep.courses.type.SessionState;
import nextstep.payments.domain.Payment;

import java.time.LocalDateTime;
import java.util.List;

public class FreeSession extends Session {

    protected FreeSession(Long id, CoverImage coverImage, CoverImages coverImages, SessionState sessionState,
                          RecruitState recruitState, int enrollment, LocalDateTime startDate, LocalDateTime endDate,
                          List<Student> students) {
        super(id, coverImage, coverImages, Enrollment.INFINITE_ENROLLMENT, enrollment, sessionState,
                recruitState, startDate, endDate, students);
    }

    @Override
    protected boolean isValidPayment(Payment payment) {
        return true;
    }

    @Override
    public long getSessionFee() {
        return 0;
    }
}
