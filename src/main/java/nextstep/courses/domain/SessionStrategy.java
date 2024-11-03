package nextstep.courses.domain;
import nextstep.courses.CannotRegisteSessionException;

public interface SessionStrategy {
    void orderSession(RequestOrderParam requestOrderParam) throws CannotRegisteSessionException;
}
