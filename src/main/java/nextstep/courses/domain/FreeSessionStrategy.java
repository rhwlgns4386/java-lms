package nextstep.courses.domain;

public class FreeSessionStrategy implements SessionStrategy {
    @Override
    public boolean applyForCourse(SessionState sessionState, int price) {
        return true;
    }
}
