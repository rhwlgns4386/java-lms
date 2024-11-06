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
    protected final Period period;
    protected final Money courseFee;
    protected final int maxStudents;
    protected final List<CoverImage> coverImages;
    protected List<SessionRegistration> registrations;
    protected SessionProgress progress;
    protected SessionRecruitmentStatus recruitment;

    protected DefaultSession(Long id, Period period, List<CoverImage> coverImages, Money courseFee, int maxStudents, SessionType type, SessionProgress progress, SessionRecruitmentStatus recruitment) {
        this(id, period, coverImages, courseFee, maxStudents, type, Collections.emptyList(), progress, recruitment);
    }

    protected DefaultSession(Long id, Period period, List<CoverImage> coverImages, Money courseFee, int maxStudents, SessionType type, List<SessionRegistration> registrations, SessionProgress progress, SessionRecruitmentStatus recruitment) {
        this.id = id;
        this.period = period;
        this.coverImages = coverImages;
        this.courseFee = courseFee;
        this.maxStudents = maxStudents;
        this.type = type;
        this.registrations = new ArrayList<>(registrations);
        this.recruitment = recruitment;
        this.progress = progress;
    }

    public void register(NsUser user, Payment payment) {
        validateDefaultStatus(user);
        validateAdditionalRequirements(user, payment);
        doRegister(user, payment);
    }

    protected abstract void validateAdditionalRequirements(NsUser user, Payment payment);

    protected abstract void doRegister(NsUser user, Payment payment);

    private void validateDefaultStatus(NsUser user) {
        if (isRegistrationUnavailable()) {
            throw new IllegalArgumentException("강의 상태가 모집 중일때만 수강신청이 가능합니다.");
        }
        if (isAlreadyRegistered(user)) {
            throw new IllegalArgumentException("이미 등록된 사용자 입니다.");
        }
        if (isFull()) {
            throw new IllegalArgumentException("수강 인원이 꽉찼습니다.");
        }
    }

    private boolean isFull() {
        return registrations.size() >= maxStudents;
    }

    private boolean isAlreadyRegistered(NsUser user) {
        return registrations.stream()
                .map(SessionRegistration::getUserId)
                .anyMatch(userId -> userId.equals(user.getId()));
    }

    public boolean isRegistrationUnavailable() {
        return recruitment == SessionRecruitmentStatus.NOT_RECRUITING || progress == SessionProgress.FINISHED;
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
        if (courseFee == null) {
            return 0L;
        }
        return courseFee.getAmount();
    }

    public Money getCourseFee() {
        return courseFee;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public SessionProgress getProgress() {
        return progress;
    }

    public SessionRecruitmentStatus getRecruitment() {
        return recruitment;
    }

    public int getMaxStudentsSize() {
        return maxStudents;
    }

    public List<CoverImage> getCoverImages() {
        return Collections.unmodifiableList(coverImages);
    }

    public List<SessionRegistration> getRegistrations() {
        return registrations;
    }

    public String getProgressStatus() {
        return progress.getCode();
    }

    public String getRecruitmentStatus() {
        return recruitment.getCode();
    }
}
