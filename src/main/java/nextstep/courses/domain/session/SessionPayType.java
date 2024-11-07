package nextstep.courses.domain.session;

import nextstep.courses.SessionException;

import java.util.Arrays;

public enum SessionPayType {
    FREE, PAID;

    public static SessionPayType search(String type) {
        return Arrays.stream(SessionPayType.values())
                .filter(value -> value.toString().equals(type))
                .findFirst()
                .orElseThrow(() -> new SessionException("강의 타입을 확인해 주세요"));
    }
}
