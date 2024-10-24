package nextstep.courses.domain;

public abstract class Session {

    private final SessionDate date;
    private final SessionImage image;
    private final SessionStatus status;
    private final int numOfStudents;

    public Session(SessionDate date, SessionImage image, SessionStatus status, int numOfStudents) {
        this.date = date;
        this.image = image;
        this.status = status;
        this.numOfStudents = numOfStudents;
    }
}
