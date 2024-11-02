package nextstep.courses.domain.session;

import nextstep.courses.domain.image.Image;
import nextstep.courses.domain.student.Student;

import java.util.ArrayList;
import java.util.List;

public class FreeSession extends Session {

    public FreeSession(String title,
                       SessionDate sessionDate,
                       Image image
    ) {
        this(image, sessionDate, null, title, SessionStatus.PREPARING, SessionType.FREE, new ArrayList<>());
    }

    public FreeSession(Long id,
                       String title,
                       SessionDate sessionDate,
                       Image image
    ) {
        this(image, sessionDate, id, title, SessionStatus.PREPARING, SessionType.FREE, new ArrayList<>());
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

    @Override
    public void register(Registration registration) {
        if (!isAvailableForRegistration()) {
            throw new IllegalStateException("Can't register session");
        }

        addStudent(Student.of(registration));
    }

}
