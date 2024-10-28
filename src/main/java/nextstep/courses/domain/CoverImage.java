package nextstep.courses.domain;

import org.springframework.lang.NonNull;

import java.awt.image.BufferedImage;
import java.io.File;

public class CoverImage {
    private static final int ONE_MEGA_BITE = 1024 * 1024;
    private static final int MIN_WIDTH_SIZE = 300;
    private static final int MIN_HEIGHT_SIZE = 200;
    private static final String SVG_EXTENSION = "svg";

    private final File file;
    private final ImageType imageType;

    public CoverImage(File file, ImageType imageType) {
        this.file = file;
        this.imageType = imageType;
    }

    public static CoverImage of(@NonNull File file) {
        String extension = FileUtils.getExtension(file);
        validateFile(file);
        return new CoverImage(file, ImageType.of(extension));
    }

    private static void validateFile(File file) {
        validateFileSize(file);
        if (!FileUtils.getExtension(file).equalsIgnoreCase(SVG_EXTENSION)) {
            validateImageFile(file);
        }
    }

    private static void validateImageFile(File file) {
        BufferedImage image = FileUtils.read(file);
        int width =  image.getWidth();
        int height = image.getHeight();
        validateImageDimensions(width, height);
        validateImageRatio(width, height);
    }

    private static void validateFileSize(File file) {
        if (file.length() > ONE_MEGA_BITE) {
            throw new IllegalArgumentException("이미지 크기는 1MB 이하여야 합니다.");
        }
    }

    private static void validateImageRatio(int width, int height) {
        if (!isThreeToTwoRatio(width, height)) {
            throw new IllegalArgumentException("이미지 너비와 높이의 비율은 3:2여야 합니다.");
        }
    }

    private static void validateImageDimensions(int width, int height) {
        if (width < MIN_WIDTH_SIZE || height < MIN_HEIGHT_SIZE) {
            throw new IllegalArgumentException("이미지 크기가 맞지 않습니다.(너비 300픽셀 이상, 높이 200픽셀 이상)");
        }
    }

    private static boolean isThreeToTwoRatio(int width, int height) {
        return width * 2 == height * 3;
    }
}
