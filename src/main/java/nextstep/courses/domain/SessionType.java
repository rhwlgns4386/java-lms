package nextstep.courses.domain;

import java.util.Arrays;

public enum SessionType {
    FREE("10"),
    PAID("20");

    private final String typeCode;

    SessionType(final String typeCode) {
        this.typeCode = typeCode;
    }

    public static SessionType fromCode(String typeCode) {
        return Arrays.stream(SessionType.values())
                .filter(value -> value.getTypeCode().equals(typeCode))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 강의 타입 코드입니다: " + typeCode));
    }

    public String getTypeCode() {
        return typeCode;
    }

}
