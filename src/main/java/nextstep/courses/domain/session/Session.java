package nextstep.courses.domain.session;

import nextstep.courses.domain.Lecturer.Lecturer;
import nextstep.courses.domain.image.Image;
import nextstep.courses.domain.student.Student;

import java.util.List;

public abstract class Session {
    private Long id;
    private String title;
    private SessionDate sessionDate;
    private List<Image> images;
    private SessionType sessionType;
    private SessionStatus sessionStatus;
    private List<Student> students;
    private Lecturer lecturer;

    public Session(Long id,
                   String title,
                   SessionDate sessionDate,
                   List<Image> images,
                   SessionType sessionType,
                   SessionStatus sessionStatus,
                   List<Student> students
    ) {
        this(id, title, sessionDate, images, sessionType, sessionStatus, students, null);
    }

    public Session(Long id,
                   String title,
                   SessionDate sessionDate,
                   List<Image> images,
                   SessionType sessionType,
                   SessionStatus sessionStatus,
                   List<Student> students,
                   Lecturer lecturer
    ) {
        this.id = id;
        this.title = title;
        this.sessionDate = sessionDate;
        this.images = images;
        this.sessionType = sessionType;
        this.sessionStatus = sessionStatus;
        this.students = students;
        this.lecturer = lecturer;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public SessionDate getSessionDate() {
        return sessionDate;
    }

    public List<Image> getImages() {
        return images;
    }

    public SessionType getSessionType() {
        return sessionType;
    }

    public List<Student> getStudents() {
        return students;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public void addLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public abstract void register(Registration registration);

    public void open() {
        this.sessionStatus.openSession();
        this.sessionStatus.startRecruiting();
    }

    public void close() {
        this.sessionStatus.endSession();
        this.sessionStatus.finishRecruiting();
    }

    public void acceptStudents(Lecturer lecturer, List<Long> studentUserIds) {
        checkStudents();
        checkLecturer(lecturer);
        checkStatus();

        this.students.stream()
                .filter(it -> studentUserIds.contains(it.getNsUserId()))
                .forEach(Student::accept);
    }

    public void rejectStudents(Lecturer lecturer, List<Long> studentUserIds) {
        checkStudents();
        checkLecturer(lecturer);
        checkStatus();

        this.students.stream()
                .filter(it -> studentUserIds.contains(it.getNsUserId()))
                .forEach(Student::reject);
    }

    private void checkStudents() {
        if (this.students.isEmpty()) {
            throw new IllegalStateException("No students found");
        }
    }

    private void checkLecturer(Lecturer lecturer) {
        if (lecturer == null || this.lecturer == null) {
            throw new IllegalArgumentException("lecturer is required");
        }
        if (!this.lecturer.equals(lecturer)) {
            throw new IllegalArgumentException("lecturer is not the same");
        }
    }

    private void checkStatus() {
        if (this.sessionStatus.isEnd()) {
            throw new IllegalStateException("Session is end");
        }
    }

    protected boolean isRegistrationAvailable() {
        return this.sessionStatus.isApplicationAvailable();
    }

    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Session)) {
            return false;
        }

        Session session = (Session) o;
        return id.equals(session.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
