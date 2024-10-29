package nextstep.courses.domain;

public interface SessionStrategy {
    boolean applyForCourse(SessionState sessionState, int price);


}
