package nextstep.courses.domain.session;

import java.util.Arrays;

public enum ImageType {
    GIF("gif"),
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    SVG("svg");

    private final String value;

    ImageType(String extension) {
        this.value = extension.toLowerCase();
    }

    public static ImageType of(String extension) {
        return Arrays.stream(values())
            .filter(it -> it.value.equalsIgnoreCase(extension))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 확장자 입니다."));
    }
}
