package nextstep.courses.domain;

import java.util.HashSet;
import java.util.Set;
import nextstep.courses.DuplicateStudentException;
import nextstep.users.domain.NsUser;

public class Enrollments {
    private final Set<NsUser> enrolledStudents;

    public Enrollments(Set<NsUser> enrolledStudents) {
        this.enrolledStudents = new HashSet<>(enrolledStudents);
    }

    public void enrollment(NsUser student) {
        validateDuplicateStudent(student);

    }

    private void validateDuplicateStudent(NsUser student) {
        if (enrolledStudents.contains(student)) {
            throw new DuplicateStudentException();
        }
    }
}
