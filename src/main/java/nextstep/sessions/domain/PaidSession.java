package nextstep.sessions.domain;

import nextstep.courses.domain.Course;
import nextstep.payments.domain.Payment;
import nextstep.sessions.CannotRegisterException;
import nextstep.studentsessions.domain.StudentSession;
import nextstep.users.domain.NsUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PaidSession extends Session {
    protected Integer maxStudent;

    public PaidSession(Long id, Course course, List<StudentSession> studentSessions, String title, Integer fee, List<Image> coverImages,
                       Integer maxStudent, SessionProgressStatus sessionStatus, SessionRecruitmentStatus sessionRecruitmentStatus,
                       LocalDate startDate, LocalDate endDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, course, studentSessions, title, coverImages, SessionFeeStatus.PAID, fee, sessionStatus, sessionRecruitmentStatus,
                startDate, endDate, createdAt, updatedAt);
        this.maxStudent = maxStudent;
    }

    public void registerSession(NsUser loginUser, Payment payment, LocalDateTime createdAt) {
        validateUser(loginUser, payment.getNsUserId());
        validatePaidSession(payment);

        registerSession(loginUser, createdAt);
    }

    private void validateUser(NsUser loginUser, Long paidUserId) {
        if (!loginUser.matchId(paidUserId)) {
            throw new CannotRegisterException("로그인한 사용자와 결제한 고객 정보가 일치하지 않습니다.");
        }
    }

    private void validatePaidSession(Payment payment) {
        validateSessionFull();
        payment.validateSessionFee(this.fee);
    }

    private void validateSessionFull() {
        if (this.maxStudent <= this.studentSessions.size()) {
            throw new CannotRegisterException("유료 강의는 강의 최대 수강 인원을 초과할 수 없습니다.");
        }
    }

}
