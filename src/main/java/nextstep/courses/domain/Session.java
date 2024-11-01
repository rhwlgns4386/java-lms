package nextstep.courses.domain;

public abstract class Session {
    private Long id;
    private String title;
    private SessionDate sessionDate;
    private Image image;
    private SessionType sessionType;
    private SessionStatus sessionStatus;

    public Session(Image image,
                   SessionDate sessionDate,
                   Long id,
                   String title,
                   SessionStatus sessionStatus,
                   SessionType sessionType
    ) {
        this.image = image;
        this.sessionDate = sessionDate;
        this.id = id;
        this.title = title;
        this.sessionStatus = sessionStatus;
        this.sessionType = sessionType;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public SessionDate getSessionDate() {
        return sessionDate;
    }

    public Image getImage() {
        return image;
    }

    public SessionType getSessionType() {
        return sessionType;
    }


    protected abstract void register(Registration registration);

    protected void open() {
        updateStatus(SessionStatus.RECRUITING);
    }

    protected void close() {
        updateStatus(SessionStatus.CLOSE);
    }

    protected boolean isAvailableForRegistration() {
        return this.sessionStatus.isOpen();
    }

    protected void updateStatus(SessionStatus sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    public SessionStatus getSessionStatus() {
        return sessionStatus;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Session)) {
            return false;
        }

        Session session = (Session) o;
        return id.equals(session.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
