package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Session {
    private final String CAN_NOT_REGISTER_SESSION = "수강신청 모집중이 아닙니다.";
    private final Long id;
    private final String title;
    private final SessionDate sessionDate;
    private final Students students;
    private final SessionImage sessionImage;
    private SessionStatus sessionStatus;

    public Session(String title, LocalDate startDay, LocalDate endDay, SessionImage sessionImage) {
        this(0L, title, startDay, endDay, SessionStatus.READY, new Students(), sessionImage);
    }

    public Session(String title, LocalDate startDay, LocalDate endDay, SessionStatus sessionStatus, Students students, SessionImage sessionImage) {
        this(0L, title, startDay, endDay, sessionStatus, students, sessionImage);
    }

    public Session(Long id, String title, LocalDate startDay, LocalDate endDay, SessionStatus sessionStatus, Students students, SessionImage sessionImage) {
        this.id = id;
        this.title = title;
        this.sessionImage = sessionImage;
        this.sessionDate = new SessionDate(startDay, endDay);
        this.sessionStatus = sessionStatus;
        this.students = students;
    }

    public void readySession() {
        changeSessionStatus(SessionStatus.READY);
    }

    public void openSession() {
        changeSessionStatus(SessionStatus.OPEN);
    }

    public void closeSession() {
        changeSessionStatus(SessionStatus.CLOSED);
    }

    private void changeSessionStatus(SessionStatus sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    public SessionStatus getSessionStatus() {
        return this.sessionStatus;
    }

    public void register(NsUser student, Payment payment) {
        if (this.sessionStatus != SessionStatus.OPEN) {
            throw new IllegalArgumentException(CAN_NOT_REGISTER_SESSION);
        }
        validateRegistration(student, payment);

        students.addStudent(student);
    }

    protected abstract void validateRegistration(NsUser student, Payment payment);

    protected abstract Long getFee();

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getSessionStart() {
        return sessionDate.getSessionStart();
    }

    public LocalDate getSessionEnd() {
        return sessionDate.getSessionEnd();
    }

    public int getImageSize() {
        return sessionImage.getImageSize();
    }

    public int getImageHeight() {
        return sessionImage.getImageHeight();
    }

    public int getImageWidth() {
        return sessionImage.getImageWidth();
    }

    public String getImageType() {
        return sessionImage.getImageType();
    }

    public int getMaxCapacity() {
        return students.getMaxCapacity();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(id, session.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, sessionDate, sessionStatus);
    }
}
