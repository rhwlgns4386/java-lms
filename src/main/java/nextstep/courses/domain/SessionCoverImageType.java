package nextstep.courses.domain;

import nextstep.qna.FileTypeException;

import java.util.Arrays;
import java.util.Locale;

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
                .orElseThrow(() -> new FileTypeException("gif, jpg, jpeg, png, svg 만 사용 가능합니다"));
    }
}
