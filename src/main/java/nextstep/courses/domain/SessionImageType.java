package nextstep.courses.domain;

import java.util.Arrays;

public enum SessionImageType {

    gif("gif"),
    jpg("jpg"),
    jpeg("jpeg"),
    png("png"),
    svg("svg");

    private final String name;

    SessionImageType(String name) {
        this.name = name;
    }

    public static SessionImageType of(String name) {
        return Arrays.stream(values())
                .filter(o -> o.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효한 이미지 타입이 아닙니다."));
    }


}
