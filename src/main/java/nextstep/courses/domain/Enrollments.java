package nextstep.courses.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import nextstep.courses.DuplicateStudentException;
import nextstep.users.domain.NsUser;

public class Enrollments {
    private SessionStatus sessionStatus;
    private final Set<NsUser> enrolledStudents;

    public Enrollments(SessionStatus sessionStatus) {
        this(sessionStatus, Set.of());
    }

    public Enrollments(SessionStatus sessionStatus, Set<NsUser> enrolledStudents) {
        this.sessionStatus = sessionStatus;
        this.enrolledStudents = new HashSet<>(enrolledStudents);
    }

    public void enrollment(NsUser student) {
        validateReayStatus();
        validateDuplicateStudent(student);
        this.enrolledStudents.add(student);
    }

    private void validateReayStatus() {
        if (!sessionStatus.isEnrolling()) {
            throw new NonReadyException();
        }
    }

    private void validateDuplicateStudent(NsUser student) {
        if (enrolledStudents.contains(student)) {
            throw new DuplicateStudentException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Enrollments that = (Enrollments) o;
        return sessionStatus == that.sessionStatus && Objects.equals(enrolledStudents, that.enrolledStudents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionStatus, enrolledStudents);
    }
}
