package nextstep.courses.domain;

import static nextstep.courses.factory.EnrollmentStudentConverter.enrollmentStudent;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import nextstep.courses.DuplicateStudentException;
import nextstep.courses.EntityNotFoundException;
import nextstep.courses.NonReadyException;
import nextstep.users.domain.NsUser;

public class Enrollments {
    private EnrollmentStatus enrollmentStatus;
    private Charge charge;
    private Set<EnrollmentStudent> enrolledStudents;

    public Enrollments(SessionStatus sessionStatus) {
        this(sessionStatus, Set.of());
    }

    public Enrollments(SessionStatus sessionStatus, Set<EnrollmentStudent> enrolledStudents) {
        this(new Charge(0), sessionStatus, enrolledStudents);
    }

    public Enrollments(Charge charge, SessionStatus sessionStatus, Set<EnrollmentStudent> enrollmentStudents) {
        this.charge = charge;
        this.enrollmentStatus = new EnrollmentStatus(sessionStatus);
        this.enrolledStudents = new HashSet<>(enrollmentStudents);
    }

    public void enrollment(int fee, Session session, NsUser user) {
        enrollment(new Charge(fee), session, user);
    }

    public void enrollment(Charge fee, Session session, NsUser student) {
        enrollment(fee, enrollmentStudent(session, student));
    }

    public void enrollment(Charge fee, EnrollmentStudent student) {
        validateCharge(fee);
        validateReadyStatus();
        validateDuplicateStudent(student);
        this.enrolledStudents.add(student);
    }

    private void validateCharge(Charge fee) {
        if (!this.charge.equals(fee)) {
            throw new IllegalArgumentException("강의 금액과 일치하지 않습니다");
        }
    }

    public void accept(NsUser student) {
        EnrollmentStudent enrollmentStudent = findByUserId(student.getId());
        enrollmentStudent.accept();
    }

    private EnrollmentStudent findByUserId(Long id) {
        return enrolledStudents.stream().filter(enrollmentStudent -> enrollmentStudent.matchesUserId(id)).findAny()
                .orElseThrow(() -> new EntityNotFoundException(EnrollmentStudent.class));
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

    public SessionStatus sessionStatus() {
        return enrollmentStatus.sessionStatus();
    }

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
        Enrollments that = (Enrollments) o;
        return Objects.equals(enrollmentStatus, that.enrollmentStatus) && Objects.equals(charge,
                that.charge) && Objects.equals(that.enrolledStudents, enrolledStudents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enrollmentStatus, charge, enrolledStudents);
    }
}
