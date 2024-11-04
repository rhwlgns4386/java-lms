package nextstep.courses.domain.session.sessionCoverImage;

import java.util.Arrays;

public enum ImageType {
    GIF("gif"),
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    SVG("svg");

    private final String type;

    ImageType(String type) {
        this.type = type;
    }

    public static void isValid(String fileType) {
        if (!hasType(fileType)) {
            throw new IllegalArgumentException("이미지 확장자는 gif, jpg, jpeg, png, svg만 가능합니다.");
        }
    }

    public static boolean hasType(String fileType) {
        return Arrays.stream(ImageType.values())
                     .anyMatch(imageType -> imageType.type.equals(fileType));
    }

}
