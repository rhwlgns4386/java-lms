package nextstep.courses.domain.session;

import nextstep.courses.domain.image.Image;
import nextstep.courses.domain.lecturer.Lecturer;
import nextstep.courses.domain.student.Student;

import java.util.ArrayList;
import java.util.List;

public class FreeSession extends Session {

    public FreeSession(String title,
                       SessionDate sessionDate,
                       List<Image> images
    ) {
        this(null, title, sessionDate, images, SessionType.FREE, SessionStatus.init(), new ArrayList<>(), null);
    }

    public FreeSession(Long id,
                       String title,
                       SessionDate sessionDate,
                       List<Image> images
    ) {
        this(id, title, sessionDate, images, SessionType.FREE, SessionStatus.init(), new ArrayList<>(), null);
    }

    public FreeSession(Long id,
                       String title,
                       SessionDate sessionDate,
                       List<Image> images,
                       SessionType sessionType,
                       SessionStatus sessionStatus,
                       List<Student> students
    ) {
        super(id, title, sessionDate, images, sessionType, sessionStatus, students, null);
    }

    public FreeSession(Long id, String title, SessionDate sessionDate, List<Image> images, SessionType sessionType, SessionStatus sessionStatus, List<Student> students, Lecturer lecturer) {
        super(id, title, sessionDate, images, sessionType, sessionStatus, students, lecturer);
    }

    public static FreeSession of(FreeSession session, List<Image> images, List<Student> students) {
        return new FreeSession(
                session.getId(),
                session.getTitle(),
                session.getSessionDate(),
                images,
                session.getSessionType(),
                session.getSessionStatus(),
                students,
                session.getLecturer()
        );
    }

    @Override
    public void register(Registration registration) {
        if (!isRegistrationAvailable()) {
            throw new IllegalStateException("Can't register session");
        }

        addStudent(Student.of(registration));
    }

}
