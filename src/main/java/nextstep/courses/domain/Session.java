package nextstep.courses.domain;

public abstract class Session {
    private SessionDate sessionDate;
    private Image image;
    private SessionType sessionType;

    public Session(SessionDate sessionDate, Image image, SessionType sessionType) {
        this.sessionDate = sessionDate;
        this.image = image;
        this.sessionType = sessionType;
    }
}
