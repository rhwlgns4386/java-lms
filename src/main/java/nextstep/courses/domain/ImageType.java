package nextstep.courses.domain;

import java.util.Arrays;

public enum ImageType {
    PNG, GIF, JPEG, JPG, SVG;

    public static boolean contains(ImageType type) {
        return Arrays.stream(ImageType.values()).anyMatch(imageType -> imageType.equals(type));
    }
}
