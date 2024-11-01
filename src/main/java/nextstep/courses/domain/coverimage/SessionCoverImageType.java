package nextstep.courses.domain.coverimage;

import nextstep.courses.CoverImageException;

import java.util.Arrays;

public enum SessionCoverImageType {
    GIF("gif"),
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    SVG("svg");

    private String extension;

    SessionCoverImageType(String extension) {
        this.extension = extension;
    }

    public static SessionCoverImageType search(String fileType) {
        return Arrays.stream(SessionCoverImageType.values())
                .filter(type -> type.extension.equalsIgnoreCase(fileType)).findFirst()
                .orElseThrow(() -> new CoverImageException("gif, jpg, jpeg, png, svg 만 사용 가능합니다"));
    }

    public String getExtension() {
        return extension;
    }
}
