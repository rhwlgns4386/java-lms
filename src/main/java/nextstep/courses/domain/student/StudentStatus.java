package nextstep.courses.domain.student;

import java.util.Arrays;

public enum StudentStatus {
    APPLIED,
    ACCEPTED,
    REJECTED;

    public static StudentStatus of(String name) {
        return Arrays.stream(values())
                .filter(it -> it.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
