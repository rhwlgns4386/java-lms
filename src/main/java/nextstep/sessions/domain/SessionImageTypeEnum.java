package nextstep.sessions.domain;

import java.util.Arrays;

public enum SessionImageTypeEnum {
    GIF("gif"),
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    SVG("svg");

    private String value;

    SessionImageTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SessionImageTypeEnum getByValue(String value) {
        return Arrays.stream(values()).filter(typeEnum->typeEnum.getValue().equals(value)).findFirst().orElseThrow();
    }
}
