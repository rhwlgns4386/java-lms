package nextstep.courses.strategy;
import nextstep.courses.exception.CannotRegisteSessionException;
import nextstep.courses.request.RequestOrderParam;

public interface SessionStrategy {
    void orderSession(RequestOrderParam requestOrderParam) throws CannotRegisteSessionException;
}
