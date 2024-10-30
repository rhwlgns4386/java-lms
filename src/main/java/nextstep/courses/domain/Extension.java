package nextstep.courses.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Extension {
    GIF("gif"),
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    SVG("svg");

    private final String extension;
    private static final Map<String, Extension> cachedExtensions = new HashMap<>();

    static {
        Arrays.stream(Extension.values())
            .forEach(it -> cachedExtensions.put(it.extension, it));
    }

    Extension(String extension) {
        this.extension = extension;
    }

    public static Extension getWithString(String extension) {
        return cachedExtensions.get(extension);
    }

    public static boolean verify(String extension) {
        return cachedExtensions.get(extension) != null;
    }
}
