package nextstep.courses.domain;

import java.util.Arrays;

public enum ImageType {
    GIF,
    JPG,
    JPEG,
    PNG,
    SVG;

    public static ImageType findByName(String name) {
        return Arrays.stream(values()).filter(v -> v.name().equalsIgnoreCase(name)).findAny()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 확장자 입니다"));
    }
}
