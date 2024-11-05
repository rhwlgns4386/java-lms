package nextstep.courses.domain;

public enum ImageType {
    GIF, JPG, JPEG, PNG, SVG;

    public static ImageType fromString(String type) {
        try {
            return ImageType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("유효하지 않은 파일 형식입니다.");
        }
    }

}
