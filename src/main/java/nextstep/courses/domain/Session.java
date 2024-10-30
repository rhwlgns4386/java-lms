package nextstep.courses.domain;

import java.util.Objects;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class Session {

    private final Long id;
    private final Long courseId;
    private Students students;
    private final Long price;
    private final SessionStatus status;
    private final Integer maxStudentSize;
    private final SessionDate sessionDate;
    private final SessionImage sessionImage;
    private final Type sessionType;

    public Session(
        Long courseId,
        Long price,
        SessionStatus status,
        Integer maxStudentSize,
        SessionDate sessionDate,
        SessionImage sessionImage,
        Type sessionType) {

        this(0L, courseId, new Students(0L), price, status, maxStudentSize, sessionDate, sessionImage, sessionType);
    }

    public Session(
        Long id,
        Long courseId,
        Long price,
        SessionStatus status,
        SessionDate sessionDate,
        SessionImage sessionImage,
        Type sessionType) {

        this(id, courseId, new Students(id), price, status, 0, sessionDate, sessionImage, sessionType);
    }

    public Session(
        Long id,
        Long courseId,
        Long price,
        SessionStatus status,
        Integer maxStudentSize,
        SessionDate sessionDate,
        SessionImage sessionImage,
        Type sessionType) {

        this(id, courseId, new Students(id), price, status, maxStudentSize, sessionDate, sessionImage, sessionType);
    }

    public Session(
        Long id,
        Long courseId,
        Students students,
        Long price,
        SessionStatus status,
        Integer maxStudentSize,
        SessionDate sessionDate,
        SessionImage sessionImage,
        Type sessionType) {

        checkFreeStudentSize(maxStudentSize, sessionType);
        this.id = id;
        this.courseId = courseId;
        this.students = students;
        this.price = price;
        this.status = status;
        this.maxStudentSize = maxStudentSize;
        this.sessionDate = sessionDate;
        this.sessionImage = sessionImage;
        this.sessionType = sessionType;
    }

    public int studentSize() {
        return this.students.size();
    }

    public void register(Payment payment, NsUser student) {
        if (SessionStatus.isNotRegister(status)) {
            throw new IllegalStateException("This session is not registering now");
        }

        if (this.sessionType.isFree()) {
            throw new IllegalStateException("This session is free");
        }

        verifyUser(payment, student);
        checkValidSession(payment);
        checkValidPrice(payment);
        addStudent(student);
    }

    public void register(NsUser student) {
        if (SessionStatus.isNotRegister(status)) {
            throw new IllegalStateException("This session is not registering now");
        }

        if (this.sessionType.isPaid()) {
            throw new IllegalStateException("This session is paid need any payment");
        }
        addStudent(student);
    }

    public void addStudents(Students students) {
        this.students = students;
    }

    private void checkFreeStudentSize(int maxStudentSize, Type sessionType) {
        if (maxStudentSize != 0 && sessionType.isFree()) {
            throw new IllegalStateException("This session is free do not set max size");
        }
    }

    private void addStudent(NsUser newStudent) {
        if (students.isBigger(this.maxStudentSize)) {
            throw new IllegalArgumentException("Max student size");
        }
        students.add(newStudent);
    }

    private void checkValidSession(Payment payment) {
        if (payment.invalidStudent(this.id)) {
            throw new IllegalArgumentException("Session Info is not valid");
        }
    }

    private void checkValidPrice(Payment payment) {
        if (!payment.isSamePrice(this.price)) {
            throw new IllegalArgumentException("Price is not valid");
        }
    }

    private static void verifyUser(Payment payment, NsUser student) {
        if (!student.verifyId(payment)) {
            throw new IllegalArgumentException("Payment is not valid");
        }
    }

    public boolean compareId(Long sessionId) {
        return Objects.equals(this.id, sessionId);
    };

    public Long getId() {
        return id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public Students getStudents() {
        return students;
    }

    public Long getPrice() {
        return price;
    }

    public SessionStatus getStatus() {
        return status;
    }

    public Integer getMaxStudentSize() {
        return maxStudentSize;
    }

    public SessionDate getSessionDate() {
        return sessionDate;
    }

    public SessionImage getSessionImage() {
        return sessionImage;
    }

    public Type getSessionType() {
        return sessionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Session)) {
            return false;
        }
        Session session = (Session)o;
        return Objects.equals(getId(), session.getId()) && Objects.equals(getCourseId(), session.getCourseId())
            && Objects.equals(getStudents(), session.getStudents()) && Objects.equals(getPrice(), session.getPrice())
            && getStatus() == session.getStatus() && Objects.equals(getMaxStudentSize(), session.getMaxStudentSize())
            && Objects.equals(getSessionDate(), session.getSessionDate()) && Objects.equals(getSessionImage(),
            session.getSessionImage()) && getSessionType() == session.getSessionType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCourseId(), getStudents(), getPrice(), getStatus(), getMaxStudentSize(), getSessionDate(), getSessionImage(),
            getSessionType());
    }
}
