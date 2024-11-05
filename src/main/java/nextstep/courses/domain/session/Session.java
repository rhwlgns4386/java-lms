package nextstep.courses.domain.session;

import nextstep.courses.domain.cover.CoverImage;
import nextstep.courses.domain.cover.CoverImages;
import nextstep.courses.domain.enrollment.Enrollment;
import nextstep.courses.domain.enrollment.Student;
import nextstep.courses.type.RecruitState;
import nextstep.courses.type.SelectionType;
import nextstep.courses.type.SessionState;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public abstract class Session {

    public static final int NOT_ASSIGNED = -1;

    protected final Long id;
    private CoverImage coverImage;
    private CoverImages coverImages;
    private SessionStatus sessionStatus;
    protected final Enrollment enrollment;
    protected final SessionDuration sessionDuration;

    protected Session(Long id, CoverImage coverImage, int maxEnrollment, int enrollment, SessionState sessionState,
                      RecruitState recruitState, SelectionType selectionType, LocalDateTime startDate, LocalDateTime endDate) {
        this(id, coverImage, CoverImages.of(coverImage), maxEnrollment, enrollment, sessionState, recruitState,
                selectionType, startDate, endDate);
    }

    protected Session(Long id, CoverImage coverImage, CoverImages coverImages, int maxEnrollment, int enrollment,
                      SessionState sessionState, RecruitState recruitState, SelectionType selectionType,
                      LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.sessionStatus = new SessionStatus(sessionState, recruitState);
        this.coverImage = coverImage;
        this.coverImages = coverImages;
        this.enrollment = new Enrollment(enrollment, maxEnrollment, selectionType);
        this.sessionDuration = new SessionDuration(startDate, endDate);
    }

    protected Session(Long id, CoverImage coverImage, CoverImages coverImages, int maxEnrollment,
                      int enrollment, SessionState sessionState, RecruitState recruitState, SelectionType selectionType,
                      LocalDateTime startDate, LocalDateTime endDate, List<Student> students) {
        this.id = id;
        this.sessionStatus = new SessionStatus(sessionState, recruitState);
        this.coverImage = coverImage;
        this.coverImages = coverImages;
        this.enrollment = new Enrollment(enrollment, maxEnrollment, students, selectionType);
        this.sessionDuration = new SessionDuration(startDate, endDate);
    }

    public final boolean apply(Student user, Payment payment) {
        validateSessionState();
        if (isValidPayment(payment)) {
            enrollment.apply(user);
            return true;
        }
        throw new IllegalStateException("결제 내역(금액 등)과 강의 수강 조건이 일치하지 않습니다");
    }

    private void validateSessionState() {
        if (!sessionStatus.canRegister()) {
            throw new IllegalStateException("모집 중 상태가 아니기 때문에 강의 신청할 수 없습니다");
        }
    }

    public final Student register(NsUser user) {
        return enrollment.register(user);
    }

    protected abstract boolean isValidPayment(Payment payment);

    public Student reject(NsUser student) {
        return enrollment.reject(student);
    }

    public Student select(NsUser student) {
        return enrollment.select(student);
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

    public final SelectionType getSelectionType() {
        return enrollment.getSelectionType();
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
