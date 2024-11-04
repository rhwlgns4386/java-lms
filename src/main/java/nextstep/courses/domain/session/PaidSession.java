package nextstep.courses.domain.session;

import nextstep.courses.domain.image.Image;
import nextstep.courses.domain.student.Student;

import java.util.ArrayList;
import java.util.List;

public class PaidSession extends Session {
    private SessionCapacity capacity;
    private Money fee;

    public PaidSession(String title,
                       List<Image> images,
                       SessionDate sessionDate,
                       SessionCapacity capacity,
                       Money fee
    ) {
        this(null, title, sessionDate, images, SessionType.PAID, LegacySessionStatus.PREPARING, new ArrayList<>(), capacity, fee);
    }

    public PaidSession(Long id,
                       String title,
                       List<Image> images,
                       SessionDate sessionDate,
                       SessionCapacity capacity,
                       Money fee
    ) {
        this(id, title, sessionDate, images, SessionType.PAID, LegacySessionStatus.PREPARING, new ArrayList<>(), capacity, fee);
    }

    public PaidSession(Long id,
                       String title,
                       SessionDate sessionDate,
                       List<Image> images,
                       SessionType sessionType,
                       LegacySessionStatus legacySessionStatus,
                       List<Student> students,
                       SessionCapacity capacity,
                       Money fee
    ) {
        super(id, title, sessionDate, images, sessionType, legacySessionStatus, students);
        this.capacity = capacity;
        this.fee = fee;
    }

    public static PaidSession of(PaidSession session, List<Image> images, List<Student> students) {
        return new PaidSession(session.getId(), session.getTitle(), session.getSessionDate(), images, session.getSessionType(), session.getSessionStatus(), students, session.getCapacity(), session.getFee());
    }

    public SessionCapacity getCapacity() {
        return capacity;
    }

    public Money getFee() {
        return fee;
    }

    @Override
    public void register(Registration registration) {
        if (!isAvailableForRegistration()) {
            throw new IllegalStateException("Can't register session");
        }
        if (registration == null) {
            throw new IllegalArgumentException("Registration must not be null");
        }
        if (!this.fee.equals(new Money(registration.getAmount()))) {
            throw new IllegalArgumentException("amount must be equal to session fee");
        }

        addStudent(Student.of(registration));
        this.capacity.checkCapacity(getStudents().size());
    }
}
