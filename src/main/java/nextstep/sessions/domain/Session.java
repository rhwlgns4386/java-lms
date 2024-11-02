package nextstep.sessions.domain;


import nextstep.courses.domain.Course;
import nextstep.registration.domain.SessionRegistrationInfo;
import nextstep.sessions.exception.CannotEnrollException;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Session {
    private Long id;

    private CoverImages images;

    private Period period;

    private SessionType type;

    private EnrolledUserInfos enrolledUserInfos;

    private SessionStatus status;

    private RecruitmentStatus recruitmentStatus;

    private Course course;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Session(Long id) {
        this.id = id;
    }

    public Session(Long id, Period period, SessionType type) {
        this(id, null, period, type, new EnrolledUserInfos(), SessionStatus.PREPARING
                , RecruitmentStatus.NOT_RECRUITING, null, LocalDateTime.now(), null);
    }

    public Session(Long id, CoverImages images, Period period, SessionType type, Course course) {
        this(id, images, period, type, new EnrolledUserInfos(), SessionStatus.PREPARING
                , RecruitmentStatus.NOT_RECRUITING, course, LocalDateTime.now(), null);
    }

    public Session(Long id, Period period, SessionType type
            , SessionStatus status, RecruitmentStatus recruitmentStatus, Course course, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this(id, null, period, type, new EnrolledUserInfos(), status , recruitmentStatus, course, createdAt, updatedAt);
    }

    public Session(Long id, CoverImages images, Period period, SessionType type
            , SessionStatus status, RecruitmentStatus recruitmentStatus, Course course, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this(id, images, period, type, new EnrolledUserInfos(), status , recruitmentStatus, course, createdAt, updatedAt);
    }

    public Session(Long id, CoverImages images, Period period, SessionType type, EnrolledUserInfos enrolledUserInfos
            , SessionStatus status, RecruitmentStatus recruitmentStatus, Course course, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.images = images;
        this.period = period;
        this.type = type;
        this.enrolledUserInfos = enrolledUserInfos;
        this.status = status;
        this.recruitmentStatus = recruitmentStatus;
        this.course = course;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public List<CoverImage> getImages() {
        return images.getImages();
    }

    public LocalDate getStartDate() {
        return period.getStartDate();
    }

    public LocalDate getEndDate() {
        return period.getEndDate();
    }

    public String getType() {
        return type.getType();
    }

    public int getMaximumEnrollment() {
        return type.getMaximumEnrollment();
    }

    public int getTuition() {
        return type.getTuition();
    }

    public EnrolledUserInfos getEnrolledUserInfos() {
        return enrolledUserInfos;
    }

    public void setEnrolledUserInfos(EnrolledUserInfos enrolledUserInfos) {
        this.enrolledUserInfos = enrolledUserInfos;
    }

    public String getStatus() {
        return status.name();
    }

    public String getRecruitmentStatus() {
        return recruitmentStatus.name();
    }

    public Long getCourseId() {
        return course.getId();
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public int getEnrolledUserInfosSize() {
        return enrolledUserInfos.size();
    }

    public Long getCreatorId() {
        return course.getCreatorId();
    }

    public SessionRegistrationInfo enroll(NsUser user, Payment payment) {
        validateEnrollment(user);
        validatePayment(payment);
        SessionRegistrationInfo info = new SessionRegistrationInfo(this, user);
        enrolledUserInfos.add(info);
        return info;
    }

    private void validateEnrollment(NsUser user) {
        if (!recruitmentStatus.isRecruiting() && !status.isEnd()) {
            throw new CannotEnrollException("모집중인 강의가 아닙니다.");
        }
        if (enrolledUserInfos.isAlreadyEnrolled(user)) {
            throw new CannotEnrollException("이미 수강 중인 사용자입니다.");
        }

        if (type.isOverEnrollment(enrolledUserInfos.getCountOfApproved())) {
            throw new CannotEnrollException("수강 인원이 초과되었습니다.");
        }
    }

    private void validatePayment(Payment payment) {
        if (type.isFree()) {
            return;
        }
        if (!payment.isComplete(type.getTuition())) {
            throw new CannotEnrollException("결제가 완료되지 않았습니다.");
        }
    }

    public void updateToRecruiting() {
        if (recruitmentStatus.isRecruiting()) {
            throw new IllegalArgumentException("이미 모집중인 강의입니다.");
        }
        recruitmentStatus = RecruitmentStatus.RECRUITING;
    }
}
