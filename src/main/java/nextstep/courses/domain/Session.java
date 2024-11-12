package nextstep.courses.domain;

import nextstep.courses.CannotOpenException;

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

    public Session(Long courseId, SessionPeriod period, SessionFeeType feeType, SessionAmount amount, int maxPersonnel, SessionProgressStatus progressStatus, SessionRecruitment recruitment) {
        this(null, courseId, period, feeType, amount, maxPersonnel, progressStatus, recruitment);
    }

    public Session(Long id, Long courseId, SessionPeriod period, SessionFeeType feeType, SessionAmount amount, int maxPersonnel, SessionProgressStatus progressStatus, SessionRecruitment recruitment) {
        this.id = id;
        this.courseId = courseId;
        this.period = period;
        this.feeType = feeType;
        this.amount = amount;
        this.maxPersonnel = maxPersonnel;
        this.progressStatus = progressStatus;
        this.recruitment = recruitment;
    }

    public static Session paidSession(Long id, Long courseId, SessionPeriod period, SessionAmount amount, int maxPersonnel, SessionProgressStatus status, SessionRecruitment recruitment) {
        validPaidSessionAmount(amount);
        return new Session(id, courseId, period, SessionFeeType.PAID, amount, maxPersonnel, status, recruitment);
    }

    private static void validPaidSessionAmount(SessionAmount amount) {
        if (Objects.equals(amount, new SessionAmount(0))) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }

    public static Session freeSession(Long id, Long courseId, SessionPeriod period, SessionProgressStatus status, SessionRecruitment recruitment) {
        return new Session(id, courseId, period, SessionFeeType.FREE, new SessionAmount(0L), 0, status, recruitment);
    }

    public static Session from(SessionCreate sessionCreate) {
        return new Session(
                sessionCreate.getCourseId(),
                new SessionPeriod(sessionCreate.getStartDate(), sessionCreate.getEndDate()),
                sessionCreate.getAmount() == 0L ? SessionFeeType.FREE : SessionFeeType.PAID,
                new SessionAmount(sessionCreate.getAmount()),
                sessionCreate.getMaxPersonnel(),
                SessionProgressStatus.PREPARING,
                SessionRecruitment.NOT_RECRUITING);
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
