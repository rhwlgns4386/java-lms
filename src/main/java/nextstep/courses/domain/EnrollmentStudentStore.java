package nextstep.courses.domain;

import java.util.HashSet;
import java.util.Set;
import nextstep.courses.DuplicateStudentException;
import nextstep.courses.EntityNotFoundException;

public class EnrollmentStudentStore {
    private Set<EnrollmentStudent> enrolledStudents;

    public EnrollmentStudentStore(Set<EnrollmentStudent> enrollmentStudents) {
        this.enrolledStudents = new HashSet<>(enrollmentStudents);
    }

    EnrollmentStudent findByUserId(Long id) {
        return enrolledStudents.stream().filter(enrollmentStudent -> enrollmentStudent.matchesUserId(id)).findAny()
                .orElseThrow(() -> new EntityNotFoundException(EnrollmentStudent.class));
    }

    public int size() {
        return enrolledStudents.size();
    }

    public Set<EnrollmentStudent> enrolledStudents() {
        return Set.copyOf(enrolledStudents);
    }

    public void add(EnrollmentStudent student) {
        validateDuplicateStudent(student);
        enrolledStudents.add(student);
    }

    private void validateDuplicateStudent(EnrollmentStudent student) {
        if (enrolledStudents.contains(student)) {
            throw new DuplicateStudentException();
        }
    }
}
