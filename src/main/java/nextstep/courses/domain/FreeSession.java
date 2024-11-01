package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;

public class FreeSession extends Session {
    private List<Student> students;

    public FreeSession(Long id,
                       String title,
                       SessionDate sessionDate,
                       Image image
    ) {
        super(image, sessionDate, id, title, SessionStatus.PREPARING, SessionType.FREE);
        this.students = new ArrayList<>();
    }

    @Override
    protected void register(Registration registration) {
        if (!isAvailableForRegistration()) {
            throw new IllegalStateException("Can't register session");
        }

        this.students.add(Student.of(registration));
    }

}
