package nextstep.courses.domain.session;

import nextstep.courses.domain.cover.CoverImage;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.util.List;

public abstract class DefaultSession {
    protected final Long id;
    protected final Status status;
    protected final Period period;
    protected final CoverImage coverImage;
    protected final Money courseFee;
    protected Capacity capacity;

    protected DefaultSession(Long id, Status status, Period period, CoverImage coverImage, Money courseFee, Capacity capacity) {
        this.id = id;
        this.status = status;
        this.period = period;
        this.coverImage = coverImage;
        this.courseFee = courseFee;
        this.capacity = capacity;
    }

    public void register(NsUser student, Payment payment) {
        validate(student, payment);
        doRegister(student, payment);
    }

    protected abstract void validate(NsUser user, Payment payment);

    protected abstract void doRegister(NsUser user, Payment payment);

    protected void validateSessionStatus() {
        if (status.isOpen()) {
            return;
        }
        throw new IllegalArgumentException("강의 상태가 모집 중일때만 수강신청이 가능합니다.");
    }

    public Long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public CoverImage getCoverImage() {
        return coverImage;
    }

    public List<Long> getRegisteredStudentIds() {
        return capacity.getRegisteredStudentIds();
    }
}
