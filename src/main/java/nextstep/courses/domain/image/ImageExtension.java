package nextstep.courses.domain.image;

import java.util.Arrays;

public enum ImageExtension {
    GIF("gif"),
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    SVG("svg");


    private final String extension;

    ImageExtension(String extension) {
        this.extension = extension;
    }

    public static ImageExtension from(String fileName) {
        String fileExtension = getFileExtension(fileName);
        return Arrays.stream(values())
                .filter(imageExtension -> imageExtension.extension.equalsIgnoreCase(fileExtension))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 확장자입니다."));
    }

    private static String getFileExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf('.');
        if (lastIndex != -1 && lastIndex < fileName.length() - 1) {
            return fileName.substring(lastIndex + 1);
        }
        throw new IllegalArgumentException("확장자가 존재하지 않습니다.");
    }
}
