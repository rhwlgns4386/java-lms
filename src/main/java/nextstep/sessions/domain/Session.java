package nextstep.sessions.domain;

import nextstep.courses.domain.Course;
import nextstep.global.domain.BaseEntity;
import nextstep.payments.domain.Payment;
import nextstep.sessions.CannotRegisterException;
import nextstep.users.domain.NsStudent;
import nextstep.users.domain.NsUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Session extends BaseEntity {
    private Course course;
    private SessionFeeStatus feeStatus;
    private String title;
    private int fee;
    private Image coverImage;
    private int maxStudent;
    private LocalDate startDate;
    private LocalDate endDate;
    private SessionStatus sessionStatus;

    private List<NsStudent> students;

    public Session(Course course, List<NsStudent> students, Long id, String title, int fee, Image coverImage, int maxStudent, SessionFeeStatus feeStatus,
                   SessionStatus sessionStatus, LocalDate startDate, LocalDate endDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, createdAt, updatedAt);
        this.course = course;
        this.students = students;
        this.title = title;
        this.fee = fee;
        this.coverImage = coverImage;
        this.maxStudent = maxStudent;
        this.feeStatus = feeStatus;
        this.sessionStatus = sessionStatus;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void registerPaidSession(NsUser loginUser, Payment payment, LocalDateTime createdAt) {
        this.validateUser(loginUser, payment.getNsUserId());
        this.validateSessionFull();
        this.sessionStatus.checkRegisterAvailable();
        payment.validateSessionFee(this.fee);

        this.registerSession(loginUser, createdAt);
    }

    public void registerSession(NsUser loginUser, LocalDateTime createdAt) {
        NsStudent student = new NsStudent(loginUser, createdAt);
        this.students.add(student);
        student.registerSession(this);
    }

    private void validateUser(NsUser loginUser, Long paidUserId) {
        if (!loginUser.matchId(paidUserId)) {
            throw new CannotRegisterException("로그인한 사용자와 결제한 고객 정보가 일치하지 않습니다.");
        }
    }

    private void validateSessionFull() {
        if (this.maxStudent == students.size()) {
            throw new CannotRegisterException("유료 강의는 강의 최대 수강 인원을 초과할 수 없습니다.");
        }
    }
}
