package nextstep.courses.domain.session;

import nextstep.courses.SessionException;

import java.util.Arrays;

public enum SessionPayType {
    FREE("free"), PAID("paid");

    private String key;

    SessionPayType(String key) {
        this.key = key;
    }

    public static SessionPayType search(String type) {
        return Arrays.stream(SessionPayType.values())
                .filter(value -> value.key.equals(type))
                .findFirst()
                .orElseThrow(()->new SessionException("강의 타입을 확인해 주세요"));
    }
}
