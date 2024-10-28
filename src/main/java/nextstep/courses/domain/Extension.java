package nextstep.courses.domain;

import java.util.Arrays;

enum Extension {
    GIF("gif"),
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    SVG("svg");

    private final String extension;

    Extension(String extension) {
        this.extension = extension;
    }

    public static boolean verify(String extension) {
        return Arrays.stream(Extension.values())
            .anyMatch(it -> it.extension.equals(extension));
    }
}
