package nextstep.courses.domain.session;

import nextstep.courses.domain.cover.CoverImage;
import nextstep.courses.domain.cover.CoverImages;
import nextstep.courses.type.RecruitState;
import nextstep.courses.type.SessionState;
import nextstep.payments.domain.Payment;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public abstract class Session {

    public static final int NOT_ASSIGNED = -1;
    static final int INIT_ENROLLMENT = 0;

    protected final Long id;
    private CoverImage coverImage;
    private CoverImages coverImages;
    private SessionStatus sessionStatus;
    protected final Enrollment enrollment;
    protected final SessionDuration sessionDuration;

    protected Session(CoverImage coverImage, int maxEnrollment, SessionState sessionState,
                      LocalDateTime startDate, LocalDateTime endDate) {
        this((long) NOT_ASSIGNED, coverImage, maxEnrollment, INIT_ENROLLMENT, sessionState, startDate, endDate);
    }

    protected Session(CoverImage coverImage, int maxEnrollment, SessionState sessionState, RecruitState recruitState,
                      LocalDateTime startDate, LocalDateTime endDate) {
        this((long) NOT_ASSIGNED, coverImage, maxEnrollment, INIT_ENROLLMENT,
                sessionState, recruitState, startDate, endDate);
    }

    protected Session(CoverImage coverImage, int maxEnrollment, int enrollment,
                      SessionState sessionState, LocalDateTime startDate, LocalDateTime endDate) {
        this((long) NOT_ASSIGNED, coverImage, maxEnrollment, enrollment, sessionState, startDate, endDate);
    }

    protected Session(CoverImage coverImage, int maxEnrollment, int enrollment, SessionState sessionState,
                      RecruitState recruitState, LocalDateTime startDate, LocalDateTime endDate) {
        this((long) NOT_ASSIGNED, coverImage, maxEnrollment, enrollment, sessionState, recruitState, startDate, endDate);
    }

    protected Session(Long id, CoverImage coverImage, int maxEnrollment, int enrollment,
                      SessionState sessionState, LocalDateTime startDate, LocalDateTime endDate) {
        this(id, coverImage, maxEnrollment, enrollment, sessionState, RecruitState.NOT_RECRUIT, startDate, endDate);
    }

    protected Session(Long id, CoverImage coverImage, int maxEnrollment, int enrollment,
                      SessionState sessionState, RecruitState recruitState,
                      LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.sessionStatus = new SessionStatus(sessionState, recruitState);
        this.coverImage = coverImage;
        this.coverImages = CoverImages.of(coverImage);
        this.enrollment = new Enrollment(enrollment, maxEnrollment);
        this.sessionDuration = new SessionDuration(startDate, endDate);
    }

    protected Session(Long id, CoverImage coverImage, CoverImages coverImages, int maxEnrollment, int enrollment,
                      SessionState sessionState, RecruitState recruitState,
                      LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.sessionStatus = new SessionStatus(sessionState, recruitState);
        this.coverImage = coverImage;
        this.coverImages = coverImages;
        this.enrollment = new Enrollment(enrollment, maxEnrollment);
        this.sessionDuration = new SessionDuration(startDate, endDate);
    }

    public final boolean register(Payment payment) {
        validateSessionState();
        enrollment.validateAvailability();
        if (isValidPayment(payment)) {
            enrollment.register();
            return true;
        }
        throw new IllegalStateException("결제 내역(금액 등)과 강의 수강 조건이 일치하지 않습니다");
    }

    private void validateSessionState() {
        if (!sessionStatus.canRegister()) {
            throw new IllegalStateException("모집 중 상태가 아니기 때문에 강의 신청할 수 없습니다");
        }
    }

    protected abstract boolean isValidPayment(Payment payment);

    public final boolean isSameId(Payment payment) {
        return payment.isSameSessionId(id);
    }

    public final void addCoverImage(String coverFilePath) {
        coverImages.add(coverFilePath);
    }

    public final void addCoverImage(File coverFile) {
        coverImages.add(coverFile);
    }

    public final void addCoverImage(CoverImage coverImage) {
        coverImages.add(coverImage);
    }

    public abstract long getSessionFee();

    public final Long getId() {
        return id;
    }

    public final SessionState getSessionState() {
        return sessionStatus.getSessionState();
    }

    public final RecruitState getRecruitState() {
        return sessionStatus.getRecruitState();
    }

    public final String getCoverFilePath() {
        return coverImage.getFilePath();
    }

    public final List<String> getCoverFilePaths() {
        return coverImages.coverImagePaths();
    }

    public final int getEnrollment() {
        return enrollment.getEnrollment();
    }

    public final int getMaxEnrollment() {
        return enrollment.getMaxEnrollment();
    }

    public final LocalDateTime getStartDate() {
        return sessionDuration.getStartDate();
    }

    public final LocalDateTime getEndDate() {
        return sessionDuration.getEndDate();
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
        return Objects.equals(id, session.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
