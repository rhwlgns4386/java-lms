package nextstep.courses.domain;

import java.util.Arrays;
import java.util.Objects;

public class SessionCoverImage {
    private static final long MAX_FILE_SIZE = 1024 * 1024;
    private static final int MIN_WIDTH = 300;
    private static final int MIN_HEIGHT = 200;
    private static final double WIDTH_HEIGHT_RATIO = 3.0 / 2.0;
    private static final String[] ALLOWED_FILE_TYPES = {"gif", "jpg", "jpeg", "png", "svg"};

    private final long fileSize;
    private final String fileType;
    private final int width;
    private final int height;

    public SessionCoverImage(long fileSize, String fileType, int width, int height) {
        validFileSize(fileSize);
        validFileType(fileType);
        validWidthAndHeight(width, height);
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.width = width;
        this.height = height;
    }

    private static boolean isExceedMaxFileSize(long fileSize) {
        return fileSize > MAX_FILE_SIZE;
    }

    private void validFileSize(long fileSize) {
        if (isExceedMaxFileSize(fileSize)) {
            throw new IllegalArgumentException("Invalid file size. Must be 1MB or less.");
        }
    }

    private static boolean isNotAllowFileTypes(String fileType) {
        return Arrays.stream(ALLOWED_FILE_TYPES)
                .noneMatch(allowedFileType -> allowedFileType.equalsIgnoreCase(fileType));
    }

    private void validFileType(String fileType) {
        if (isNotAllowFileTypes(fileType)) {
            throw new IllegalArgumentException("Invalid file type: " + fileType);
        }
    }

    private static boolean isLessMinSize(int width, int height) {
        return width < MIN_WIDTH || height < MIN_HEIGHT;
    }

    private boolean isAspectRatio(int width, int height) {
        double ratio = (double) width / height;
        return Math.abs(ratio - WIDTH_HEIGHT_RATIO) >= 0.01;
    }

    private void validWidthAndHeight(int width, int height) {
        if (isLessMinSize(width, height)) {
            throw new IllegalArgumentException("Invalid image dimensions: " + width + "x" + height);
        }
        if (isAspectRatio(width, height)) {
            throw new IllegalArgumentException("Invalid aspect ratio: " + width + "x" + height);
        }
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SessionCoverImage that = (SessionCoverImage) o;
        return fileSize == that.fileSize && width == that.width && height == that.height && Objects.equals(fileType, that.fileType);
    }
}
