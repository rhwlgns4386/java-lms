package nextstep.courses.domain;

import java.util.HashSet;
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
}
