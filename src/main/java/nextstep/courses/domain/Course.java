package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.sessions.Session;
import nextstep.sessions.Sessions;
import nextstep.users.domain.Student;

import java.time.LocalDateTime;

public class Course {
    private Long id;
    private final CourseDetail courseDetail;
    private final CourseDate courseDate;

    private Sessions sessions = new Sessions();

    public Course(String title, Long creatorId, int cohort) {
        this(0L, new CourseDetail(title, creatorId, cohort), new CourseDate(LocalDateTime.now(), LocalDateTime.now()));
    }

    public Course(Long id, CourseDetail courseDetail, CourseDate courseDate) {
        this.id = id;
        this.courseDetail = courseDetail;
        this.courseDate = courseDate;
    }

    public int getCohort() {
        return courseDetail.getCohort();
    }

    public void addSession(Session session) {
        sessions.addSession(session);
    }

    public Payment processPayment(Student student, Long amount, Session session) {
        registerStudentInSession(student, session, amount);
        return processReceipt(student, session, amount);
    }

    public String getTitle() {
        return courseDetail.getTitle();
    }

    public LocalDateTime getCreatedAt() {
        return courseDate.getCreatedAt();
    }

    public Long getCreatorId() {
        return courseDetail.getCreatorId();
    }

    public Long getId() {
        return id;
    }

    public Sessions getSessions() {
        return new Sessions(sessions.clone());
    }

    public boolean contains(Session session) {
        return sessions.contains(session);
    }

    public int getSessionSize() {
        return sessions.size();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Course course = (Course) o;
        return id.equals(course.id);
    }

    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + courseDetail.getTitle() + '\'' +
                ", creatorId=" + courseDetail.getCreatorId() +
                ", cohort=" + courseDetail.getCohort() +
                ", createdAt=" + courseDate.getCreatedAt() +
                ", updatedAt=" + courseDate.getUpdatedAt() +
                '}';
    }

    private void registerStudentInSession(Student student, Session session, Long amount) {
        sessions.validateSession(session);
        session.register(student, amount);
    }

    private Payment processReceipt(Student student, Session session, Long amount) {
        return new Payment("0", this.id, session.getId(), student.getId(), amount);
    }
}
