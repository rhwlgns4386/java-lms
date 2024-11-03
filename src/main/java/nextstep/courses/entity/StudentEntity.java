package nextstep.courses.entity;

import lombok.Getter;
import nextstep.courses.domain.enrollment.Student;
import nextstep.courses.type.EnrollmentState;
import nextstep.users.domain.NsUser;

@Getter
public class StudentEntity {

    private Long id;
    private EnrollmentState enrollmentState;
    private NsUser student;

    public StudentEntity(EnrollmentState enrollmentState, NsUser student) {
        this(null, enrollmentState, student);
    }

    public StudentEntity(Long id, EnrollmentState enrollmentState, NsUser student) {
        this.id = id;
        this.enrollmentState = enrollmentState;
        this.student = student;
    }

    public Student toDomain() {
        return new Student(id, student, enrollmentState);
    }

    public static StudentEntity from(Student student) {
        return new StudentEntity(student.getId(), student.getEnrollmentState(), student.getStudent());
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", enrollmentState=" + enrollmentState +
                ", student=" + student +
                '}';
    }
}
