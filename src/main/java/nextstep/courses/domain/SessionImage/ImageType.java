package nextstep.courses.domain.SessionImage;

import java.util.Arrays;

public enum ImageType {

    gif,
    jpg,
    jpeg,
    png,
    svg;


    public static ImageType validateType(String imageType) {
        return Arrays.stream(values()).filter(type -> type.name().equals(imageType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이미지 타입이 올바르지 않습니다."));
    }
}
