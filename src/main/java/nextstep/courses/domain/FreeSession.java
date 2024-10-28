package nextstep.courses.domain;

import java.util.Objects;

import nextstep.users.domain.NsUser;

public class FreeSession extends Session {

    private final Long id;
    private final Long courseId;
    private final Students students;
    private final SessionStatus status;

    public FreeSession(
        Long id,
        Long courseId,
        SessionDate sessionDate,
        SessionImage sessionImage,
        SessionStatus sessionStatus,
        Type sessionType)
    {
        this(
            id,
            courseId,
            sessionDate,
            sessionImage,
            sessionStatus,
            sessionType,
            new Students()
        );
    }

    public FreeSession(
        Long id,
        Long courseId,
        SessionDate sessionDate,
        SessionImage sessionImage,
        SessionStatus sessionStatus,
        Type sessionType,
        Students students)
    {
        super(sessionDate,
            sessionImage,
            sessionType);
        this.id = id;
        this.courseId = courseId;
        this.students = students;
        this.status = sessionStatus;
    }

    public void addStudent(NsUser student) {
        if (!(status == SessionStatus.RESITER)) {
            throw new IllegalStateException("This session is not registering now");
        }
        students.add(student);
    }

    @Override
    public boolean compareId(Long sessionId) {
        return this.id == sessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof FreeSession))
            return false;
        if (!super.equals(o))
            return false;
        FreeSession that = (FreeSession)o;
        return Objects.equals(id, that.id) && Objects.equals(courseId, that.courseId) && Objects.equals(students,
            that.students) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, courseId, students, status);
    }
}
