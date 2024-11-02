package nextstep.courses.domain.session;

import nextstep.courses.domain.image.Image;
import nextstep.courses.domain.student.Student;

import java.util.ArrayList;
import java.util.List;

public class PaidSession extends Session {
    private SessionCapacity capacity;
    private Money fee;
    private List<Student> students;

    public PaidSession(String title,
                       Image image,
                       SessionDate sessionDate,
                       SessionCapacity capacity,
                       Money fee
    ) {
        this(image, sessionDate, null, title, SessionStatus.PREPARING, SessionType.PAID, capacity, fee, new ArrayList<>());
    }

    public PaidSession(Long id,
                       String title,
                       Image image,
                       SessionDate sessionDate,
                       SessionCapacity capacity,
                       Money fee
    ) {
        this(image, sessionDate, id, title, SessionStatus.PREPARING, SessionType.PAID, capacity, fee, new ArrayList<>());
    }

    public PaidSession(Image image,
                       SessionDate sessionDate,
                       Long id,
                       String title,
                       SessionStatus sessionStatus,
                       SessionType sessionType,
                       SessionCapacity capacity,
                       Money fee,
                       List<Student> students
    ) {
        super(image, sessionDate, id, title, sessionStatus, sessionType);
        this.capacity = capacity;
        this.fee = fee;
        this.students = students;
    }

    public SessionCapacity getCapacity() {
        return capacity;
    }

    public Money getFee() {
        return fee;
    }

    public List<Student> getStudents() {
        return students;
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

        this.students.add(Student.of(registration));
        this.capacity.updateCurrentCount(this.students.size());
    }
}
