package nextstep.courses.domain.session.sessioncoverimage;

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

    public static ImageType findByFileType(String fileType) {
        return Arrays.stream(ImageType.values())
                     .filter(imageType -> imageType.type.equals(fileType))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("이미지 확장자는 gif, jpg, jpeg, png, svg만 가능합니다."));
    }

}
