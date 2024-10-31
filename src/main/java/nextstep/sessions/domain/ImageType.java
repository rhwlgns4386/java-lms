package nextstep.sessions.domain;

import java.util.stream.Stream;

public enum ImageType {
    GIF, JPG, JPEG, PNG, SVG;

    public static ImageType of(String type) {
        return Stream.of(values())
                .filter(imageType -> imageType.name().equals(type))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 이미지 타입입니다."));
    }
}
