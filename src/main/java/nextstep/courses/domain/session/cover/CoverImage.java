package nextstep.courses.domain.session.cover;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

public class CoverImage {

    private static final Set<String> ALLOWED_FILE_EXTENSIONS
            = new HashSet<>(Arrays.asList("gif", "jpeg", "jpg", "png"));
    private static final int MAX_FILE_SIZE = 1024 * 1024;

    private static final int MIN_WIDTH = 300;
    private static final int MIN_HEIGHT = 200;

    private final File source;

    public CoverImage(File imagePath) {
        this.source = imagePath;
    }

    public static CoverImage of(String imagePath) {
        File imageFile = new File(imagePath);
        validate(imageFile);
        return new CoverImage(imageFile);
    }

    private static void validate(File imageFile) {
        validateFile(imageFile);
        validateImagePixel(imageFile);
    }

    private static void validateFile(File imageFile) {
        if (!ALLOWED_FILE_EXTENSIONS.contains(parseExtension(imageFile.getName()))) {
            throw new IllegalArgumentException("invalid");
        }
        if (imageFile.length() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("invalid");
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
        System.out.println((double) width / height);
        return (double) width / height;
    }

    public File getSource() {
        return source;
    }
}
