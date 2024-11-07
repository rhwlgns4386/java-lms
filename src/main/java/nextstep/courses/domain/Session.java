package nextstep.courses.domain;

import nextstep.courses.CannotOpenException;
import nextstep.courses.NotPendingException;

import java.util.List;
import java.util.Objects;

public class Session {
    private final Long id;

    private final Long courseId;

    private final SessionPeriod period;

    private final SessionFeeType feeType;

    private final SessionAmount amount;

    private final int maxPersonnel;

    private SessionProgressStatus progressStatus;

    private SessionRecruitment recruitment;

    private SessionApprovalStatus approvalStatus;

    public Session(Long courseId, SessionPeriod period, SessionFeeType feeType, SessionAmount amount, int maxPersonnel, SessionProgressStatus progressStatus, SessionRecruitment recruitment, SessionApprovalStatus approvalStatus) {
        this(null, courseId, period, feeType, amount, maxPersonnel, progressStatus, recruitment, approvalStatus);
    }

    public Session(Long id, Long courseId, SessionPeriod period, SessionFeeType feeType, SessionAmount amount, int maxPersonnel, SessionProgressStatus progressStatus, SessionRecruitment recruitment, SessionApprovalStatus approvalStatus) {
        this.id = id;
        this.courseId = courseId;
        this.period = period;
        this.feeType = feeType;
        this.amount = amount;
        this.maxPersonnel = maxPersonnel;
        this.progressStatus = progressStatus;
        this.recruitment = recruitment;
        this.approvalStatus = approvalStatus;
    }

    public static Session paidSession(Long id, Long courseId, SessionPeriod period, SessionAmount amount, int maxPersonnel, SessionProgressStatus status, SessionRecruitment recruitment, SessionApprovalStatus approvalStatus) {
        validPaidSessionAmount(amount);
        return new Session(id, courseId, period, SessionFeeType.PAID, amount, maxPersonnel, status, recruitment, approvalStatus);
    }

    private static void validPaidSessionAmount(SessionAmount amount) {
        if (Objects.equals(amount, new SessionAmount(0))) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }

    public static Session freeSession(Long id, Long courseId, SessionPeriod period, SessionProgressStatus status, SessionRecruitment recruitment, SessionApprovalStatus approvalStatus) {
        return new Session(id, courseId, period, SessionFeeType.FREE, new SessionAmount(0L), 0, status, recruitment, approvalStatus);
    }

    public static Session from(SessionCreate sessionCreate) {
        return new Session(
                sessionCreate.getCourseId(),
                new SessionPeriod(sessionCreate.getStartDate(), sessionCreate.getEndDate()),
                sessionCreate.getAmount() == 0L ? SessionFeeType.FREE : SessionFeeType.PAID,
                new SessionAmount(sessionCreate.getAmount()),
                sessionCreate.getMaxPersonnel(),
                SessionProgressStatus.PREPARING,
                SessionRecruitment.NOT_RECRUITING,
                SessionApprovalStatus.PENDING);
    }

    public void open() {
        if (!progressStatus.isPreparing()) {
            throw new CannotOpenException("It cannot be opened." + this.progressStatus);
        }
        this.progressStatus = SessionProgressStatus.PROGRESSING;
    }

    public SessionApply sessionApply(List<Student> students) {
        return new SessionApply(maxPersonnel, recruitment, progressStatus, students);
    }

    public void updateApprovalStatus(SessionApprovalStatus sessionApprovalStatus) {
        if (!approvalStatus.isPending()) {
            throw new NotPendingException("It cannot be approved." + this.approvalStatus);
        }
        this.approvalStatus = sessionApprovalStatus;
    }

    public Long getId() {
        return id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public SessionPeriod getPeriod() {
        return period;
    }

    public SessionFeeType getFeeType() {
        return feeType;
    }

    public SessionAmount getAmount() {
        return amount;
    }

    public int getMaxPersonnel() {
        return maxPersonnel;
    }

    public SessionProgressStatus getProgressStatus() {
        return progressStatus;
    }

    public SessionRecruitment getRecruitment() {
        return recruitment;
    }

    public SessionApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Session session = (Session) o;
        return maxPersonnel == session.maxPersonnel && Objects.equals(id, session.id) && Objects.equals(courseId, session.courseId) && Objects.equals(period, session.period) && feeType == session.feeType && Objects.equals(amount, session.amount) && progressStatus == session.progressStatus;
    }
}
