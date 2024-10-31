package nextstep.courses.domain;

public class Student {
    private Long nsUserId;
    private Long amount;

    public Student(Long amount, Long nsUserId) {
        this.amount = amount;
        this.nsUserId = nsUserId;
    }

    public static Student of(Registration registration){
        return new Student(registration.getAmount(), registration.getNsUserId());
    }

    public Long getAmount() {
        return amount;
    }

    public Long getNsUserId() {
        return nsUserId;
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
