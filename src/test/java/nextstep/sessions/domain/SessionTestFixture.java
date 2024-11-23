package nextstep.sessions.domain;

import nextstep.courses.domain.Course;
import nextstep.studentsessions.domain.StudentSession;
import nextstep.users.NsUserTestFixture;
import nextstep.users.domain.NsStudent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class SessionTestFixture<T extends SessionTestFixture<T>> {
    protected Long id = 0L;
    protected Course course = new Course();
    protected String title = "TEST_SESSION_TITLE";
    protected List<Image> coverImages = new ArrayList<>();
    protected LocalDate startDate = LocalDate.now();
    protected LocalDate endDate = LocalDate.now().plusDays(40);
    protected SessionProgressStatus sessionStatus = SessionProgressStatus.ONGOING;
    protected SessionRecruitmentStatus sessionRecruitmentStatus = SessionRecruitmentStatus.OPEN;
    protected LocalDateTime createdAt = LocalDateTime.now();
    protected List<StudentSession> studentSessions = new ArrayList<>() {{
        add(new StudentSession(null, new NsStudent(new NsUserTestFixture().createdAt(LocalDateTime.now()).build(), LocalDateTime.now()), LocalDateTime.now()));
    }};
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

    public T coverImages(List<Image> coverImages) {
        this.coverImages = coverImages;
        return (T) this;
    }

    public T studentSessions(StudentSession studentSession) {
        this.studentSessions.add(studentSession);
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

    public T sessionStatus(SessionProgressStatus sessionStatus) {
        this.sessionStatus = sessionStatus;
        return (T) this;
    }

    public T sessionRecruitmentStatus(SessionRecruitmentStatus sessionRecruitmentStatus) {
        this.sessionRecruitmentStatus = sessionRecruitmentStatus;
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
            return new FreeSession(this.id, this.course, this.studentSessions, this.title, this.coverImages,
                    this.sessionStatus, this.sessionRecruitmentStatus, this.startDate, this.endDate, this.createdAt, this.updatedAt);
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
            return new PaidSession(id, course, studentSessions, title, fee, coverImages, maxStudent, sessionStatus,
                    sessionRecruitmentStatus, startDate, endDate, createdAt, null);
        }
    }
}
