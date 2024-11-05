package nextstep.courses.domain.cover;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.imageio.ImageIO;

public class CoverImage {

    private static final Set<String> ALLOWED_FILE_EXTENSIONS
            = new HashSet<>(Arrays.asList("gif", "jpeg", "jpg", "png", "svg"));
    private static final int MAX_FILE_SIZE = 1024 * 1024;

    private static final int MIN_WIDTH = 300;
    private static final int MIN_HEIGHT = 200;

    private final File source;

    private CoverImage(File imagePath) {
        this.source = imagePath;
    }

    public static CoverImage of(String imagePath) {
        if (imagePath == null || imagePath.isBlank()) {
            throw new IllegalArgumentException("강의 커버 파일 경로는 null 또는 공백일 수 없습니다");
        }
        return of(new File(imagePath));
    }

    public static CoverImage of(File imageFile) {
        validate(imageFile);
        return new CoverImage(imageFile);
    }

    private static void validate(File imageFile) {
        validateFile(imageFile);
        validateImagePixel(imageFile);
    }

    private static void validateFile(File imageFile) {
        if (imageFile == null) {
            throw new IllegalArgumentException("빈 파일입니다");
        }
        if (!ALLOWED_FILE_EXTENSIONS.contains(parseExtension(imageFile.getName()))) {
            throw new IllegalArgumentException("지원하지 않는 파일 확장자입니다");
        }
        if (imageFile.length() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("파일 사이즈는 1mb를 초과할 수 없습니다");
        }
    }

    private static String parseExtension(String filename) {
        int index = filename.lastIndexOf(".");
        return (index > -1) ? filename.substring(index + 1).toLowerCase() : "";
    }

    public static void validateImagePixel(File file) {
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();

            if (width < MIN_WIDTH || height < MIN_HEIGHT) {
                throw new IllegalArgumentException("강의 커버 이미지는 가로 300px 이상, 세로 200px 이상이여야 합니다.");
            }

            if (getRadio(width, height) != getRadio(3, 2)) {
                throw new IllegalArgumentException("강의 커버 이미지는 가로 세로 비율이 3:2 여야 합니다.");
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private static double getRadio(int width, int height) {
        return (double) width / height;
    }

    public String getFilePath() {
        try {
            return source.getCanonicalPath();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CoverImage that = (CoverImage) o;
        return Objects.equals(source, that.source);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(source);
    }
}
