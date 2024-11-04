package nextstep.courses.domain.session;

import nextstep.courses.domain.image.Image;
import nextstep.courses.domain.student.Student;

import java.util.ArrayList;
import java.util.List;

public class FreeSession extends Session {

    public FreeSession(String title,
                       SessionDate sessionDate,
                       List<Image> images
    ) {
        this(null, title, sessionDate, images, SessionType.FREE, SessionStatus.PREPARING, new ArrayList<>());
    }

    public FreeSession(Long id,
                       String title,
                       SessionDate sessionDate,
                       List<Image> images
    ) {
        this(id, title, sessionDate, images, SessionType.FREE, SessionStatus.PREPARING, new ArrayList<>());
    }

    public FreeSession(Image image,
                       SessionDate sessionDate,
                       Long id,
                       String title,
                       SessionStatus sessionStatus,
                       SessionType sessionType,
                       List<Student> students
    ) {
        super(image, sessionDate, id, title, sessionStatus, sessionType, students);
    }

    public FreeSession(Long id,
                       String title,
                       SessionDate sessionDate,
                       List<Image> images,
                       SessionType sessionType,
                       SessionStatus sessionStatus,
                       List<Student> students
    ) {
        super(id, title, sessionDate, images, sessionType, sessionStatus, students);
    }

    public static FreeSession of(FreeSession session, Image image, List<Student> students) {
        return new FreeSession(image, session.getSessionDate(), session.getId(), session.getTitle(), session.getSessionStatus(), session.getSessionType(), students);
    }

    public static FreeSession of(FreeSession session, List<Image> images, List<Student> students) {
        return new FreeSession(session.getId(), session.getTitle(), session.getSessionDate(), images, session.getSessionType(), session.getSessionStatus(), students);
    }

    @Override
    public void register(Registration registration) {
        if (!isAvailableForRegistration()) {
            throw new IllegalStateException("Can't register session");
        }

        addStudent(Student.of(registration));
    }

}
