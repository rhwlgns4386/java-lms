package nextstep.courses.domain.session;

import nextstep.courses.domain.image.Image;
import nextstep.courses.domain.student.Student;

import java.util.List;

public abstract class Session {
    private Long id;
    private String title;
    private SessionDate sessionDate;
    private List<Image> images;
    private SessionType sessionType;
    private LegacySessionStatus legacySessionStatus;
    private SessionStatus sessionStatus;
    private List<Student> students;

    public Session(Long id,
                   String title,
                   SessionDate sessionDate,
                   List<Image> images,
                   SessionType sessionType,
                   LegacySessionStatus legacySessionStatus,
                   List<Student> students
    ) {
        this.id = id;
        this.title = title;
        this.sessionDate = sessionDate;
        this.images = images;
        this.sessionType = sessionType;
        this.legacySessionStatus = legacySessionStatus;
        this.students = students;
    }

    public Session(Long id,
                   String title,
                   SessionDate sessionDate,
                   List<Image> images,
                   SessionType sessionType,
                   SessionStatus sessionStatus,
                   List<Student> students
    ) {
        this.id = id;
        this.title = title;
        this.sessionDate = sessionDate;
        this.images = images;
        this.sessionType = sessionType;
        this.sessionStatus = sessionStatus;
        this.students = students;
    }

    public Session(Long id,
                   String title,
                   SessionDate sessionDate,
                   List<Image> images,
                   SessionType sessionType,
                   LegacySessionStatus legacySessionStatus,
                   SessionStatus sessionStatus,
                   List<Student> students
    ) {
        this.id = id;
        this.title = title;
        this.sessionDate = sessionDate;
        this.images = images;
        this.sessionType = sessionType;
        this.legacySessionStatus = legacySessionStatus;
        this.sessionStatus = sessionStatus;
        this.students = students;
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

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public abstract void register(Registration registration);

    public void open() {
        updateStatus(LegacySessionStatus.RECRUITING);
        this.sessionStatus.openSession();
        this.sessionStatus.startRecruiting();
    }

    public void close() {
        updateStatus(LegacySessionStatus.CLOSE);
        this.sessionStatus.endSession();
        this.sessionStatus.finishRecruiting();
    }

    protected boolean isRegistrationAvailable() {
        return this.sessionStatus.isApplicationAvailable();
    }

    protected void updateStatus(LegacySessionStatus legacySessionStatus) {
        this.legacySessionStatus = legacySessionStatus;
    }

    public LegacySessionStatus getLegacySessionStatus() {
        return legacySessionStatus;
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
