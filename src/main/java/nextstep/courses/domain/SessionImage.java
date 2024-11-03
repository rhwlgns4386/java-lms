package nextstep.courses.domain;

import java.util.List;

public class SessionImage {
    private static final int MAX_SIZE_BYTES = 1 * 1024 * 1024; // 바이트 단위로 변환
    private static final int MIN_PIXEL_WIDTH = 300;
    private static final int MIN_PIXEL_HEIGHT = 200;
    private static final int RATIO_WIDTH = 3;
    private static final int RATIO_HEIGHT = 2;

    private static final List<String> FILE_EXTENSIONS = List.of("gif", "jpg", "jpeg", "png", "svg");

    private final int fileSize;
    private final String type;
    private final int width;
    private final int height;
    private final String fileName;

    public SessionImage(final int fileSize, final String type, final int width, final int height, final String fileName) {
        validationSessionImage(fileSize, type, width, height);
        this.fileSize = fileSize;
        this.type = type;
        this.width = width;
        this.height = height;
        this.fileName = fileName;
    }

    private static void validationSessionImage(int fileSize, String type, int width, int height) {
        validateFileSize(fileSize);
        validateType(type);
        validatePixel(width, height);
        validateRatio(width, height);
    }

    private static void validateRatio(int width, int height) {
        if (width * RATIO_HEIGHT != height * RATIO_WIDTH) {
            throw new IllegalArgumentException("width와 height의 비율은 3:2여야 한다.");
        }
    }

    private static void validatePixel(int width, int height) {
        if (width < MIN_PIXEL_WIDTH || height < MIN_PIXEL_HEIGHT) {
            throw new IllegalArgumentException("이미지의 width는 300픽셀, height는 200픽셀 이상이어야 합니다.");
        }
    }

    private static void validateType(String type) {
        if (!FILE_EXTENSIONS.contains(type)) {
            throw new IllegalArgumentException("이미지 타입은 gif, jpg(jpeg 포함),png, svg만 허용합니다.");
        }
    }

    private static void validateFileSize(int fileSize) {
        if (fileSize >= MAX_SIZE_BYTES) {
            throw new IllegalArgumentException("이미지 크기는 1MB이하여야 합니다.");
        }
    }

}
