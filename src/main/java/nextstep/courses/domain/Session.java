package nextstep.courses.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Session {


    private final SessionImage sessionImage;
    private final SessionState sessionState;
    private final SessionStrategy sessionStrategy;
    private final LocalDateTime createAt;
    private final LocalDateTime endAt;

    public Session(int size, String imageType, int width, int height, SessionState sessionState, SessionStrategy sessionStrategy) {
        this(new SessionImage(size, imageType, width, height), sessionState, sessionStrategy);
    }

    public Session(SessionImage sessionImage, SessionState sessionState, SessionStrategy sessionStrategy) {
        this.sessionImage = sessionImage;
        this.sessionState = sessionState;
        this.sessionStrategy = sessionStrategy;
        this.createAt = LocalDateTime.now();
        this.endAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Session)) return false;
        Session session = (Session) o;
        return Objects.equals(sessionImage, session.sessionImage) && sessionState == session.sessionState && Objects.equals(sessionStrategy, session.sessionStrategy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionImage, sessionState, sessionStrategy);
    }

    public boolean applyForCourse(int price) {
        return this.sessionStrategy.applyForCourse(this.sessionState, price);
    }
}
