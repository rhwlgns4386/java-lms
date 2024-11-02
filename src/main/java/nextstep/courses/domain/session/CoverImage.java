package nextstep.courses.domain.session;

public class CoverImage {
    private static final int MAX_FILE_SIZE = 1024 * 1024; // 1MB
    private static final int MIN_WIDTH_SIZE = 300;
    private static final int MIN_HEIGHT_SIZE = 200;

    private final int size;
    private final int width;
    private final int height;
    private final ImageType imageType;

    public CoverImage(int size, int width, int height, ImageType imageType) {
        this.size = size;
        this.width = width;
        this.height = height;
        this.imageType = imageType;
    }

    public static CoverImage of(ImageFile file) {
        validateFile(file);
        return new CoverImage(file.getSize(), file.getWidth(), file.getHeight(), ImageType.of(file.getExtension()));
    }

    private static void validateFile(ImageFile file) {
        validateFileSize(file);
        validateImageFile(file);
    }

    private static void validateImageFile(ImageFile file) {
        int width = file.getWidth();
        int height = file.getHeight();
        validateImageDimensions(width, height);
        validateImageRatio(width, height);
    }

    private static void validateFileSize(ImageFile file) {
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("강의 이미지 크기는 1MB 이하여야 합니다.");
        }
    }

    private static void validateImageRatio(int width, int height) {
        if (!isThreeToTwoRatio(width, height)) {
            throw new IllegalArgumentException("강의 이미지 너비와 높이의 비율은 3:2여야 합니다.");
        }
    }

    private static void validateImageDimensions(int width, int height) {
        if (width < MIN_WIDTH_SIZE || height < MIN_HEIGHT_SIZE) {
            throw new IllegalArgumentException("강의 이미지 크기가 맞지 않습니다.(너비 300픽셀 이상, 높이 200픽셀 이상)");
        }
    }

    private static boolean isThreeToTwoRatio(int width, int height) {
        return width * 2 == height * 3;
    }
}
