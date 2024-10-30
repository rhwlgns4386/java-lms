package nextstep.sessions.domain;

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

}
