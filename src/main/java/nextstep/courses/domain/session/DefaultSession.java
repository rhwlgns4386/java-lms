package nextstep.courses.domain.session;

import nextstep.courses.domain.common.Column;
import nextstep.courses.domain.cover.CoverImage;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.util.Collections;
import java.util.List;

public abstract class DefaultSession {
    // 실제 필드는 없지만 DB 매핑을 위해 어노테이션 추가
    @Column(name = "session_type")
    protected static String TYPE;
    protected final Long id;

    protected final SessionStatus status;
    // Period는 start_date와 end_date 두 컬럼에 매핑되므로 두 개의 @Column 사용
    @Column(name = "start_date", subField = "startDate")
    @Column(name = "end_date", subField = "endDate")
    protected final Period period;
    @Column(name = "cover_image_id")
    protected final CoverImage coverImage;
    @Column(name = "course_fee")
    protected final Money courseFee;
    protected final SessionRegistrations registrations;
    private final List<CoverImage> coverImages;
    @Column(name = "max_students", subField = "maxStudents")
    protected Capacity capacity;

    protected DefaultSession(Long id, SessionStatus status, Period period, List<CoverImage> coverImages, Money courseFee, Capacity capacity) {
        this.id = id;
        this.status = status;
        this.period = period;
        this.coverImage = coverImages.get(0);
        this.coverImages = coverImages;
        this.courseFee = courseFee;
        this.capacity = capacity;
        this.registrations = new SessionRegistrations(id, capacity.getMaxStudentsSize());
    }

    protected DefaultSession(Long id, SessionStatus status, Period period, List<CoverImage> coverImages, Money courseFee, SessionRegistrations registrations) {
        this.id = id;
        this.status = status;
        this.period = period;
        this.coverImage = coverImages.get(0);
        this.coverImages = coverImages;
        this.courseFee = courseFee;
        this.registrations = registrations;
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

    public Long getId() {
        return id;
    }

    public CoverImage getCoverImage() {
        return coverImage;
    }

    public List<Long> getRegisteredStudentIds() {
        return registrations.getApprovedUserIds();
    }

    public Period getPeriod() {
        return period;
    }

    public SessionRegistrations getRegistrations() {
        return registrations;
    }

    public List<CoverImage> getCoverImages() {
        return Collections.unmodifiableList(coverImages);
    }

    public SessionStatus getStatus() {
        return status;
    }
}
