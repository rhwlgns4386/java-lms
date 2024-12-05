package nextstep.courses.domain;

import static nextstep.courses.factory.EnrollmentStudentConverter.enrollmentStudent;

import java.util.Objects;
import java.util.Set;
import nextstep.courses.NonReadyException;
import nextstep.users.domain.NsUser;

public class Enrollments {
    private EnrollmentStudentStore enrollmentStudentStore;
    private EnrollmentStatus enrollmentStatus;
    private Charge charge;

    public Enrollments(SessionStatus sessionStatus) {
        this(sessionStatus, Set.of());
    }

    public Enrollments(SessionStatus sessionStatus, Set<EnrollmentStudent> enrolledStudents) {
        this(new Charge(0), sessionStatus, enrolledStudents);
    }

    public Enrollments(Charge charge, SessionStatus sessionStatus, Set<EnrollmentStudent> enrollmentStudents) {
        this(charge, new EnrollmentStatus(sessionStatus), new EnrollmentStudentStore(enrollmentStudents));
    }

    private Enrollments(Charge charge, EnrollmentStatus enrollmentStatus, EnrollmentStudentStore studentStore) {
        this.charge = charge;
        this.enrollmentStatus = enrollmentStatus;
        this.enrollmentStudentStore = studentStore;
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
        this.enrollmentStudentStore.add(student);
    }

    private void validateCharge(Charge fee) {
        if (!this.charge.equals(fee)) {
            throw new IllegalArgumentException("강의 금액과 일치하지 않습니다");
        }
    }

    private void validateReadyStatus() {
        if (!enrollmentStatus.isRecruiting()) {
            throw new NonReadyException();
        }
    }

    public void accept(NsUser student) {
        EnrollmentStudent enrollmentStudent = findByUserId(student);
        enrollmentStudent.accept();
    }

    public void reject(NsUser student) {
        EnrollmentStudent enrollmentStudent = findByUserId(student);
        enrollmentStudent.reject();
    }

    private EnrollmentStudent findByUserId(NsUser student) {
        return enrollmentStudentStore.findByUserId(student.getId());
    }

    protected int size() {
        return this.enrollmentStudentStore.size();
    }

    public SessionStatus sessionStatus() {
        return enrollmentStatus.sessionStatus();
    }

    public Set<EnrollmentStudent> enrolledStudents() {
        return enrollmentStudentStore.enrolledStudents();
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
                that.charge) && Objects.equals(that.enrolledStudents(),
                enrollmentStudentStore.enrolledStudents());
    }

    @Override
    public int hashCode() {
        return Objects.hash(enrollmentStatus, charge, enrollmentStudentStore.enrolledStudents());
    }
}
