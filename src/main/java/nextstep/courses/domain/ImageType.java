package nextstep.courses.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public enum ImageType {
    GIF("gif"),
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    SVG("svg");

    private static final Set<String> ALLOWED_EXTENSIONS = Arrays.stream(ImageType.values())
            .map(type -> type.extension.toLowerCase())
            .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));

    private static final String INVALID_IMG_TYPE = "유효하지 않은 이미지 타입입니다.";

    private final String extension;

    ImageType(String extension) {
        this.extension = extension;
    }

    public static ImageType fromString(String type) {
        if (isValidType(type)) {
            return ImageType.valueOf(type.toUpperCase());
        }
        throw new IllegalArgumentException(INVALID_IMG_TYPE);
    }

    public static boolean isValidType(String extension) {
        return ALLOWED_EXTENSIONS.contains(extension.toLowerCase());
    }

    @Override
    public String toString() {
        return this.extension;
    }
}
