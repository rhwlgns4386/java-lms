package nextstep.courses.domain.image;

import java.util.Arrays;

public enum CoverImageExtensionType {
    PNG("png"), JPG("jpg"),
    GIF("gif"), SVG("svg");

    private static final String EXTENSION_ERROR_MESSAGE = "옳바른 확장자명이 아닙니다.";

    private String extension;

    CoverImageExtensionType(String extension) {
        this.extension = extension;
    }

    public static CoverImageExtensionType valueOfExtension(String fileName) {
        String extension = extractExtension(fileName);
        return Arrays.stream(values())
                .filter(value -> value.getExtension().equalsIgnoreCase(extension))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(EXTENSION_ERROR_MESSAGE));
    }

    private static String extractExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            throw new IllegalArgumentException(EXTENSION_ERROR_MESSAGE);
        }
        return fileName.substring(lastDotIndex + 1);
    }

    public String getExtension() {
        return extension;
    }

}
