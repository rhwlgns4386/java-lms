package nextstep.courses.domain.Image;

import java.util.Arrays;

public enum ImageType {
    PNG, GIF, JPEG, JPG, SVG;

    public static boolean contains(String type) {
        return Arrays.stream(ImageType.values()).anyMatch(imageType -> imageType.equals(type));
    }
    public static boolean contains(ImageType type) {
        return Arrays.stream(ImageType.values()).anyMatch(imageType -> imageType.equals(type));
    }

    public static void validateType(String type) {
        if (!ImageType.contains(type)) {
            throw new IllegalArgumentException("지원하지 않는 이미지 형식입니다.");
        }
    }

    public static void validateType(ImageType type) {
        if (!ImageType.contains(type)) {
            throw new IllegalArgumentException("지원하지 않는 이미지 형식입니다.");
        }
    }
}
