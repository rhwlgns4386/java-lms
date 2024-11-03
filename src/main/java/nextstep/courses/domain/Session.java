package nextstep.courses.domain;

import java.util.Objects;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class Session {

    private final Long id;
    private final Long courseId;
    private final Long teacherId;
    private Students students;
    private final Long price;
    private final SessionStatus sessionStatus;
    private final RegisterStatus registerStatus;
    private final Integer maxStudentSize;
    private final SessionDate sessionDate;
    private Images sessionImages;
    private final Type sessionType;

    public Session(
        Long id,
        Long courseId,
        Long teacherId,
        Long price,
        SessionStatus sessionStatus,
        RegisterStatus registerStatus,
        int maxStudentSize,
        SessionDate sessionDate,
        Type sessionType) {

        this(id, courseId, teacherId, new Students(id), price, sessionStatus, registerStatus, maxStudentSize, sessionDate, new Images(id), sessionType);
    }

    public Session(
        Long id,
        Long courseId,
        Long teacherId,
        Long price,
        SessionStatus sessionStatus,
        RegisterStatus registerStatus,
        Integer maxStudentSize,
        SessionDate sessionDate,
        Images sessionImages,
        Type sessionType) {

        this(id, courseId, teacherId, new Students(id), price, sessionStatus, registerStatus, maxStudentSize, sessionDate, sessionImages, sessionType);
    }

    public Session(
        Long id,
        Long courseId,
        Long teacherId,
        Students students,
        Long price,
        SessionStatus sessionStatus,
        RegisterStatus registerStatus,
        Integer maxStudentSize,
        SessionDate sessionDate,
        Images sessionImages,
        Type sessionType) {

        checkFreeStudentSize(maxStudentSize, sessionType);
        this.id = id;
        this.courseId = courseId;
        this.teacherId = teacherId;
        this.students = students;
        this.price = price;
        this.sessionStatus = sessionStatus;
        this.registerStatus = registerStatus;
        this.maxStudentSize = maxStudentSize;
        this.sessionDate = sessionDate;
        this.sessionImages = sessionImages;
        this.sessionType = sessionType;
    }

    public int studentSize() {
        return this.students.size();
    }

    public void register(Payment payment, NsUser student) {
        if (SessionStatus.cannotRegister(sessionStatus) || RegisterStatus.isNotRegister(registerStatus)) {
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
        if (SessionStatus.cannotRegister(sessionStatus) || RegisterStatus.isNotRegister(registerStatus)) {
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

    public void addImages(Images images) {
        this.sessionImages = images;
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

    public Long getTeacherId() {
        return teacherId;
    }

    public Students getStudents() {
        return students;
    }

    public Long getPrice() {
        return price;
    }

    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }

    public RegisterStatus getRegisterStatus() {
        return registerStatus;
    }

    public Integer getMaxStudentSize() {
        return maxStudentSize;
    }

    public SessionDate getSessionDate() {
        return sessionDate;
    }

    public Images getSessionImages() {
        return sessionImages;
    }

    public Type getSessionType() {
        return sessionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Session))
            return false;
        Session session = (Session)o;
        return Objects.equals(getId(), session.getId()) && Objects.equals(getCourseId(), session.getCourseId())
            && Objects.equals(getStudents(), session.getStudents()) && Objects.equals(getPrice(), session.getPrice())
            && getSessionStatus() == session.getSessionStatus() && getRegisterStatus() == session.getRegisterStatus() && Objects.equals(
            getMaxStudentSize(), session.getMaxStudentSize()) && Objects.equals(getSessionDate(), session.getSessionDate())
            && Objects.equals(getSessionImages(), session.getSessionImages()) && getSessionType() == session.getSessionType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCourseId(), getStudents(), getPrice(), getSessionStatus(), getRegisterStatus(), getMaxStudentSize(),
            getSessionDate(), getSessionImages(), getSessionType());
    }
}
