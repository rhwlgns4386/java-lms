package nextstep.sessions.domain;

import nextstep.courses.domain.Course;
import nextstep.users.domain.NsStudent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class SessionTestFixture<T extends SessionTestFixture<T>> {
    protected Long id = 0L;
    protected Course course = new Course();
    protected SessionFeeStatus feeStatus = SessionFeeStatus.FREE;
    protected String title = "TEST_SESSION_TITLE";
    protected Image coverImage = new Image();
    protected LocalDate startDate = LocalDate.now();
    protected LocalDate endDate = LocalDate.now().plusDays(40);
    protected SessionStatus sessionStatus = SessionStatus.RECRUITING;
    protected List<NsStudent> students = new ArrayList<>();
    protected LocalDateTime createdAt = LocalDateTime.now();
    protected LocalDateTime updatedAt = LocalDateTime.now().plusHours(1);

    public static Paid paidSessionBuilder() {
        return new Paid();
    }

    public static Free freeSessionBuilder() {
        return new Free();
    }

    public T id(Long id) {
        this.id = id;
        return (T) this;
    }

    public T course(Course course) {
        this.course = course;
        return (T) this;
    }

    public T title(String title) {
        this.title = title;
        return (T) this;
    }

    public T coverImage(Image coverImage) {
        this.coverImage = coverImage;
        return (T) this;
    }

    public T students(List<NsStudent> students) {
        this.students = students;
        return (T) this;
    }

    public T startDate(LocalDate startDate) {
        this.startDate = startDate;
        return (T) this;
    }

    public T endDate(LocalDate endDate) {
        this.endDate = endDate;
        return (T) this;
    }

    public T sessionStatus(SessionStatus sessionStatus) {
        this.sessionStatus = sessionStatus;
        return (T) this;
    }

    public T createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return (T) this;
    }

    public T updatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return (T) this;
    }

    public static class Free extends SessionTestFixture<Free> {
        public FreeSession build() {
            return new FreeSession(this.id, this.course, this.students, this.title, this.coverImage, this.sessionStatus,
                    this.startDate, this.endDate, this.createdAt, this.updatedAt);
        }
    }


    public static class Paid extends SessionTestFixture<Paid> {
        private Integer fee = 10000;
        private Integer maxStudent = 30;

        public SessionTestFixture.Paid fee(Integer fee) {
            this.fee = fee;
            return this;
        }

        public SessionTestFixture.Paid maxStudent(Integer maxStudent) {
            this.maxStudent = maxStudent;
            return this;
        }

        public PaidSession build() {
            return new PaidSession(id, course, students, title, fee, coverImage, maxStudent, sessionStatus,
                    startDate, endDate, createdAt, null);
        }
    }
}
