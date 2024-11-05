package nextstep.courses.domain.student;

import nextstep.courses.domain.session.Registration;

import java.util.List;
import java.util.stream.Collectors;

public class Student {
    private Long nsUserId;
    private Long amount;
    private StudentStatus status;

    public Student(Long amount, Long nsUserId) {
        this(nsUserId, amount, StudentStatus.APPLIED);
    }

    public Student(Long nsUserId, Long amount, StudentStatus status) {
        if (status == null) {
            throw new NullPointerException("status must not be null");
        }
        this.nsUserId = nsUserId;
        this.amount = amount;
        this.status = status;
    }

    public static Student of(Registration registration) {
        return new Student(registration.getAmount(), registration.getNsUserId());
    }

    public static List<Student> of(List<Registration> registrations) {
        return registrations.stream()
                .map(it -> new Student(it.getAmount(), it.getNsUserId()))
                .collect(Collectors.toList());
    }

    public Long getAmount() {
        return amount;
    }

    public Long getNsUserId() {
        return nsUserId;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public void accept() {
        this.status = StudentStatus.ACCEPTED;
    }

    public void reject() {
        this.status = StudentStatus.REJECTED;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }

        Student student = (Student) o;
        return nsUserId.equals(student.nsUserId);
    }

    @Override
    public int hashCode() {
        return nsUserId.hashCode();
    }
}
