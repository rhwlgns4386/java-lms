package nextstep.courses.domain;

import java.util.Objects;

public abstract class Session {
    private final SessionDate sessionDate;
    private final SessionImage sessionImage;
    private final Type sessionType;

    public Session(
        SessionDate sessionDate,
        SessionImage sessionImage,
        Type sessionType)
    {
        this.sessionDate = sessionDate;
        this.sessionImage = sessionImage;
        this.sessionType = sessionType;
    }

    public abstract boolean compareId(Long sessionId);

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Session))
            return false;
        Session session = (Session)o;
        return Objects.equals(sessionDate, session.sessionDate) && Objects.equals(sessionImage, session.sessionImage)
            && sessionType == session.sessionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionDate, sessionImage, sessionType);
    }
}
