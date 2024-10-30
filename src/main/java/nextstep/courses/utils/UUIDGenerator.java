package nextstep.courses.utils;

import java.util.UUID;

public class UUIDGenerator {

    private UUIDGenerator() {
        throw new IllegalStateException("인스턴스를 생성할 수 없습니다.");
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
}
