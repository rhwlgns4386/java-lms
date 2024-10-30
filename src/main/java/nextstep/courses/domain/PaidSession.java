package nextstep.courses.domain;

import nextstep.courses.MaxStudentCapacityException;
import nextstep.courses.domain.session.CoverImage;
import nextstep.courses.domain.session.DateRange;
import nextstep.courses.domain.session.Status;
import nextstep.courses.domain.session.Students;
import nextstep.payments.PaymentMismatchException;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.Objects;

public class PaidSession extends Session {

    public static final String MAX_STUDENT_CAPACITY_MESSAGE = "강의 최대 수강인원을 초과하였습니다.";
    public static final String PAYMENT_MISMATCH_MESSAGE = "금액이 맞지 않습니다.";
    private final int maxRegisterCount;
    private final long amount;
    private final Students students;

    public PaidSession(long id,
                       long creatorId,
                       long courseId,
                       DateRange dateRange,
                       CoverImage coverImage,
                       Status status,
                       int maxRegisterCount,
                       long amount,
                       LocalDateTime createdAt,
                       LocalDateTime updatedAt) {
        super(id, courseId, dateRange, coverImage, status, creatorId, createdAt, updatedAt);
        this.maxRegisterCount = maxRegisterCount;
        this.amount = amount;
        this.students = new Students();
    }

    public void register(Payment payment) {
        if (maxRegisterCount <= students.size()) {
            throw new MaxStudentCapacityException(MAX_STUDENT_CAPACITY_MESSAGE);
        }
        if (!payment.matchAmount(amount)) {
            throw new PaymentMismatchException(PAYMENT_MISMATCH_MESSAGE);
        }
        NsUser payingUser = payment.payingUser();
        this.students.add(new Student(this, payingUser, createdAt));
    }

    public int getMaxRegisterCount() {
        return maxRegisterCount;
    }

    public long getAmount() {
        return amount;
    }

    public Object[] toPaidParameters() {
        return new Object[]{
                id, maxRegisterCount, amount
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PaidSession that = (PaidSession) o;
        return maxRegisterCount == that.maxRegisterCount && amount == that.amount && Objects.equals(students, that.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), maxRegisterCount, amount, students);
    }

    @Override
    public String toString() {
        return "PaidSession{" +
                "maxRegisterCount=" + maxRegisterCount +
                ", amount=" + amount +
                ", students=" + students +
                '}';
    }
}
