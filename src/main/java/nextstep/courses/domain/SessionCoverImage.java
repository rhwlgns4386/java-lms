package nextstep.courses.domain;

import java.util.Arrays;
import java.util.Objects;

public class SessionCoverImage {
    private final long MAX_FILE_SIZE = 1024 * 1024;
    private final int MIN_WIDTH = 300;
    private final int MIN_HEIGHT = 200;
    private final double WIDTH_HEIGHT_RATIO = 3.0 / 2.0;
    private final String[] ALLOWED_FILE_TYPES = {"gif", "jpg", "jpeg", "png", "svg"};

    private long fileSize;
    private String fileType;
    private int width;
    private int height;

    public SessionCoverImage(long fileSize, String fileType, int width, int height) {
        if (!isFileSizeValid(fileSize)) {
            throw new IllegalArgumentException("Invalid file size. Must be 1MB or less.");
        }
        if (!isFileTypeValid(fileType)) {
            throw new IllegalArgumentException("Invalid file type: " + fileType);
        }
        if (!isImageDimensionsValid(width, height)) {
            throw new IllegalArgumentException("Invalid image dimensions: " + width + "x" + height);
        }
        if (!isAspectRatioValid(width, height)) {
            throw new IllegalArgumentException("Invalid aspect ratio: " + width + "x" + height);
        }
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.width = width;
        this.height = height;
    }

    private boolean isFileSizeValid(long length) {
        return length <= MAX_FILE_SIZE;
    }

    private boolean isFileTypeValid(String fileType) {
        return Arrays.stream(ALLOWED_FILE_TYPES)
                .anyMatch(allowedFileType -> allowedFileType.equalsIgnoreCase(fileType));
    }

    private boolean isImageDimensionsValid(int width, int height) {
        return width >= MIN_WIDTH && height >= MIN_HEIGHT;
    }

    private boolean isAspectRatioValid(int width, int height) {
        double ratio = (double) width / height;
        return Math.abs(ratio - WIDTH_HEIGHT_RATIO) < 0.01;
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
        return fileSize == that.fileSize && width == that.width && height == that.height && Objects.deepEquals(ALLOWED_FILE_TYPES, that.ALLOWED_FILE_TYPES) && Objects.equals(fileType, that.fileType);
    }
}
