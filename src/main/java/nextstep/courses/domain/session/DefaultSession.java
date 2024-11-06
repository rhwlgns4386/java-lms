package nextstep.courses.domain.session;

import nextstep.courses.domain.cover.CoverImage;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class DefaultSession {
    protected final Long id;
    protected final SessionType type;
    protected final SessionStatus status;
    protected final Period period;
    protected final Money courseFee;
    protected final int maxStudents;
    private final List<CoverImage> coverImages;
    protected List<SessionRegistration> registrations;

    protected DefaultSession(Long id, SessionStatus status, Period period, List<CoverImage> coverImages, Money courseFee, int maxStudents, SessionType type) {
        this(id, status, period, coverImages, courseFee, maxStudents, type, Collections.emptyList());
    }

    protected DefaultSession(Long id, SessionStatus status, Period period, List<CoverImage> coverImages, Money courseFee, int maxStudents, SessionType type, List<SessionRegistration> registrations) {
        this.id = id;
        this.status = status;
        this.period = period;
        this.coverImages = coverImages;
        this.courseFee = courseFee;
        this.maxStudents = maxStudents;
        this.type = type;
        this.registrations = new ArrayList<>(registrations);
    }

    public void register(NsUser student, Payment payment) {
        validateSessionStatus();
        validate(student, payment);
        doRegister(student, payment);
    }

    protected abstract void validate(NsUser user, Payment payment);

    protected abstract void doRegister(NsUser user, Payment payment);

    private void validateSessionStatus() {
        if (status.canRegister()) {
            return;
        }
        throw new IllegalArgumentException("강의 상태가 모집 중일때만 수강신청이 가능합니다.");
    }

    public long getId() {
        return id;
    }

    public Period getPeriod() {
        return period;
    }

    public String getTypeCode() {
        return type.getCode();
    }

    public Long getCourseFeeAmount() {
        if(courseFee == null){
            return 0L;
        }
        return courseFee.getAmount();
    }

    public int getMaxStudentsSize() {
        return maxStudents;
    }

    public List<CoverImage> getCoverImages() {
        return Collections.unmodifiableList(coverImages);
    }

    public SessionStatus getStatus() {
        return status;
    }

    public List<SessionRegistration> getRegistrations() {
        return registrations;
    }
}
