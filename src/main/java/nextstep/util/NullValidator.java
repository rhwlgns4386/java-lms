package nextstep.util;

import java.util.Objects;

public class NullValidator {

    public static void validateNull(Object... objects) {
        for (Object object : objects) {
            Objects.requireNonNull(object);
        }
    }
}
