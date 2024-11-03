package nextstep.courses.domain.session;

import nextstep.courses.domain.cover.CoverImage;
import nextstep.courses.domain.common.Column;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDate;
import java.util.List;

public abstract class DefaultSession {
    protected final Long id;

    @Column(name = "status")
    protected final Status status;

    // Period는 start_date와 end_date 두 컬럼에 매핑되므로 두 개의 @Column 사용
    @Column(name = "start_date", subField = "startDate")
    @Column(name = "end_date", subField = "endDate")
    protected final Period period;

    @Column(name = "cover_image_id")
    protected final CoverImage coverImage;

    @Column(name = "course_fee")
    protected final Money courseFee;

    @Column(name = "max_students", subField = "maxStudents")
    protected Capacity capacity;

    // 실제 필드는 없지만 DB 매핑을 위해 어노테이션 추가
    @Column(name = "session_type")
    protected static String TYPE;

    protected DefaultSession(Long id, Status status, Period period, CoverImage coverImage, Money courseFee, Capacity capacity) {
        this.id = id;
        this.status = status;
        this.period = period;
        this.coverImage = coverImage;
        this.courseFee = courseFee;
        this.capacity = capacity;
    }

    public void register(NsUser student, Payment payment) {
        validateSessionStatus();
        validate(student, payment);
        doRegister(student, payment);
    }

    protected abstract void validate(NsUser user, Payment payment);

    protected abstract void doRegister(NsUser user, Payment payment);

    private void validateSessionStatus() {
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

    public Long getCoverImageId() {
        return coverImage.getId();
    }

    public List<Long> getRegisteredStudentIds() {
        return capacity.getRegisteredStudentIds();
    }

    public Period getPeriod() {
        return period;
    }

    public LocalDate getStartDate() {
        return period.getStartDate();
    }

    public LocalDate getEndDate() {
        return period.getEndDate();
    }

    public long getCourseFee() {
        return courseFee.getAmount();
    }

    public Capacity getCapacity() {
        return capacity;
    }
}
