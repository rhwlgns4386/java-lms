package nextstep.courses.domain.session.cover.validator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import nextstep.courses.domain.session.cover.ImageValidator;

public class ImagePixelValidator implements ImageValidator {

    private static final int MIN_WIDTH = 300;
    private static final int MIN_HEIGHT = 200;

    @Override
    public void validate(File file) {
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

    private double getRadio(int width, int height) {
        System.out.println((double) width / height);
        return (double) width / height;
    }
}
