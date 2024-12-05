package nextstep.courses.domain;

import static nextstep.courses.factory.EnrollmentStudentConverter.enrollmentStudent;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import nextstep.courses.DuplicateStudentException;
import nextstep.courses.NonReadyException;
import nextstep.users.domain.NsUser;

public class DefaultEnrollments implements Enrollments {
    private SessionStatus sessionStatus;
    private EnrollmentStatus enrollmentStatus;
    private final Set<EnrollmentStudent> enrolledStudents;

    public DefaultEnrollments(SessionStatus sessionStatus) {
        this(sessionStatus, Set.of());
    }

    public DefaultEnrollments(SessionStatus sessionStatus, Set<EnrollmentStudent> enrolledStudents) {
        this.sessionStatus = sessionStatus;
        this.enrollmentStatus = EnrollmentStatus.findBySessionStatus(sessionStatus);
        this.enrolledStudents = new HashSet<>(enrolledStudents);
    }

    @Override
    public void enrollment(Session session, NsUser student) {
        enrollment(enrollmentStudent(session, student));
    }

    @Override
    public void enrollment(EnrollmentStudent student) {
        validateReadyStatus();
        validateDuplicateStudent(student);
        this.enrolledStudents.add(student);
    }

    private void validateReadyStatus() {
        if (!enrollmentStatus.isRecruiting()) {
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

    @Override
    public SessionStatus sessionStatus() {
        return sessionStatus;
    }

    @Override
    public Set<EnrollmentStudent> enrolledStudents() {
        return Set.copyOf(enrolledStudents);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DefaultEnrollments that = (DefaultEnrollments) o;
        return sessionStatus == that.sessionStatus && Objects.equals(enrolledStudents, that.enrolledStudents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionStatus, enrolledStudents);
    }
}
