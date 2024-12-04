package nextstep.courses.factory;

import nextstep.courses.domain.Image;
import nextstep.courses.domain.ImageType;

public class ImageConverter {
    private ImageConverter() {
    }

    public static Image toImage(String fileName, int width, int height, int size, ImageType imageType) {
        return new Image(fileName, width, height, size, imageType);
    }
}
