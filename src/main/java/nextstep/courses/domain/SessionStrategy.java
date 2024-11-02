package nextstep.courses.domain;
import nextstep.courses.CannotRegisteSessionException;

public interface SessionStrategy {
    void validateOrderSession(RequestOrderParam requestOrderParam) throws CannotRegisteSessionException;
    void orderSession(RequestOrderParam requestOrderParam) throws CannotRegisteSessionException;
}
