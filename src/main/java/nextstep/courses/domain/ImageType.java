package nextstep.courses.domain;

import java.util.Arrays;

public enum ImageType {
    GIF("gif"),
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    SVG("svg");
    private final String extension;

    ImageType(String extension) {
        this.extension = extension;
    }

    public static ImageType getExtension(String type) {
        return Arrays.stream(values())
                .filter(it -> it.extension.equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 확장자는 허용하지 않습니다."));
    }
}
