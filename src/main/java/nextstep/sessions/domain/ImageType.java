package nextstep.sessions.domain;

import java.util.Arrays;
import java.util.List;

public enum ImageType {
    GIF(List.of("gif")),
    JPG(List.of("jpg", "jpeg")),
    PNG(List.of("png")),
    SVG(List.of("svg"));

    private final List<String> value;

    ImageType(List<String> value) {
        this.value = value;
    }

    public static ImageType of(String type) {
        return Arrays.stream(ImageType.values())
                .filter(v -> v.value.contains(type.toLowerCase()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("'%s'는 허용하지 않는 이미지 타입입니다.", type)));
    }
}
