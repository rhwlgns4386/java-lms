package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class Session {

    private final Long id;

    private final Long courseId;

    private final SessionPeriod dateRange;

    private final Status status;

    private final SessionImage image;

    private final Pricing pricing;

    private final SessionStudents students;

    private final int availableSeats;

    private final Long creatorId;

    private final LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Session(Long courseId, SessionPeriod dateRange, Status status, SessionImage image, Pricing pricing, int availableSeats, Long creatorId, LocalDateTime createdAt) {
        this(0L, courseId, dateRange, status, image, pricing, null, availableSeats, creatorId, createdAt);
    }

    public Session(Long courseId, SessionPeriod dateRange, Status status, SessionImage image, Pricing pricing, SessionStudents student, int availableSeats, Long creatorId, LocalDateTime createdAt) {
        this(0L, courseId, dateRange, status, image, pricing, student, availableSeats, creatorId, createdAt);
    }

    public Session(Long courseId, SessionPeriod dateRange, SessionImage image, Pricing pricing, SessionStudents student, int availableSeats, Long creatorId, LocalDateTime createdAt) {
        this(0L, courseId, dateRange, new Status(ProgressStatus.PREPARING, RecruitmentStatus.RECRUITING), image, pricing, student, availableSeats, creatorId, createdAt);
    }

    public Session(Long id, Long courseId, SessionPeriod dateRange, Status status, SessionImage image, Pricing pricing, SessionStudents student, int availableSeats, Long creatorId, LocalDateTime createdAt) {
        this.id = id;
        this.courseId = courseId;
        this.dateRange = dateRange;
        this.status = status;
        this.image = image;
        this.pricing = pricing;
        this.students = student;
        this.availableSeats = availableSeats;
        this.creatorId = creatorId;
        this.createdAt = createdAt;
    }

    public void enrollStudent(NsUser loginUser, int payAmount) {
        checkEnrollmentPermission(payAmount);
        students.addStudent(new SessionStudent(id, loginUser.getId()));
    }

    public void filterSelectedStudents(){
        students.filterSelectedStudents();
    }

    //region checkEnrollmentPermission
    private void checkEnrollmentPermission(int paymentAmount) {
        validateStatus();
        validatePrice(paymentAmount);
        checkFullSession();
    }

    private void validateStatus() {
        if (status.canNotEnroll()) {
            throw new IllegalStateException("강의 상태가 모집 중이 아니면 수강 신청 할 수 없습니다.");
        }
    }

    private void validatePrice(int paymentAmount) {
        if (pricing.canNotEnroll(paymentAmount)) {
            throw new IllegalStateException("강의 가격과 지불한 돈이 일치하지 않습니다.");
        }
    }

    private void checkFullSession() {
        if (pricing.isFree()) {
            return;
        }

        if (students.size() == availableSeats) {
            throw new IllegalStateException("이 강의는 정원이 초과되었습니다.");
        }
    }
    //endregion

    public Long getId() {
        return id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public SessionPeriod getDateRange() {
        return dateRange;
    }

    public Status getStatus() {
        return status;
    }

    public SessionImage getImage() {
        return image;
    }

    public Pricing getPricing() {
        return pricing;
    }

    public SessionStudents getStudents() {
        return students;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
