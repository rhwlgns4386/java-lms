package nextstep.courses.domain;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum Extension {
    GIF("gif"),
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    SVG("svg");

    private final String extension;
    private static Map<String, Extension> cachedExtensions;

    Extension(String extension) {
        this.extension = extension;
    }

    private static Map<String, Extension> getCachedExtensions() {
        cachedExtensions = Arrays.stream(Extension.values())
            .collect(Collectors.toMap(
                ext -> ext.extension,
                ext -> ext
            ));
        return cachedExtensions;
    }

    public static Extension getWithString(String extension) {
        return getCachedExtensions().get(extension);
    }
}
