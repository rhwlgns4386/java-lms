package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class EnrollmentStudentTest {

    @Test
    void accept() {
        EnrollmentStudent enrollmentStudent = new EnrollmentStudent(0, 0, RequestStatus.PENDING);
        enrollmentStudent.accept();

        assertThat(enrollmentStudent).isEqualTo(new EnrollmentStudent(0, 0, RequestStatus.ACCEPTED));
    }

    @Test
    void reject() {
        EnrollmentStudent enrollmentStudent = new EnrollmentStudent(0, 0, RequestStatus.PENDING);
        enrollmentStudent.reject();

        assertThat(enrollmentStudent).isEqualTo(new EnrollmentStudent(0, 0, RequestStatus.REJECT));
    }

    @Test
    void matchesUserId() {
        EnrollmentStudent enrollmentStudent = new EnrollmentStudent(1, 0, RequestStatus.PENDING);

        assertThat(enrollmentStudent.matchesUserId(0L)).isTrue();
    }
}
