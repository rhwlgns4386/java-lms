package nextstep.users.domain;

import java.util.Arrays;

public enum UserType {
    STUDENT,
    LECTURER;

    public static UserType of(String name) {
        return Arrays.stream(values())
                .filter(it -> it.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
