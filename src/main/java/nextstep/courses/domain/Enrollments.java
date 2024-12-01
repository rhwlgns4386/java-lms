package nextstep.courses.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import nextstep.courses.DuplicateStudentException;
import nextstep.courses.NonReadyException;
import nextstep.users.domain.NsUser;

public class Enrollments {
    private SessionStatus sessionStatus;
    private final Set<EnrollmentStudent> enrolledStudents;

    public Enrollments(SessionStatus sessionStatus) {
        this(sessionStatus, Set.of());
    }

    public Enrollments(Session session, SessionStatus sessionStatus) {
        this(sessionStatus, session, Set.of());
    }

    public Enrollments(SessionStatus sessionStatus, Session session, Set<NsUser> enrolledStudents) {
        this(sessionStatus, toStudentSet(session, enrolledStudents));
    }

    public Enrollments(SessionStatus sessionStatus, Set<EnrollmentStudent> enrolledStudents) {
        this.sessionStatus = sessionStatus;
        this.enrolledStudents = new HashSet<>(enrolledStudents);
    }

    public void enrollment(Session session, NsUser student) {
        enrollment(new EnrollmentStudent(session, student));
    }

    public void enrollment(EnrollmentStudent student) {
        validateReadyStatus();
        validateDuplicateStudent(student);
        this.enrolledStudents.add(student);
    }

    private void validateReadyStatus() {
        if (!sessionStatus.isEnrolling()) {
            throw new NonReadyException();
        }
    }

    private void validateDuplicateStudent(EnrollmentStudent student) {
        if (enrolledStudents.contains(student)) {
            throw new DuplicateStudentException();
        }
    }

    protected int size() {
        return this.enrolledStudents.size();
    }

    private static Set<EnrollmentStudent> toStudentSet(Session session, Set<NsUser> enrolledStudents) {
        return enrolledStudents.stream().map((user) -> new EnrollmentStudent(session, user))
                .collect(Collectors.toSet());
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
