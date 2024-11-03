package nextstep.courses.domain.session;

import java.util.Arrays;

public enum SessionType {
    FREE,
    PAID;

    public static SessionType of(String name) {
        return Arrays.stream(values())
                .filter(it -> it.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid session type: " + name));
    }
}
