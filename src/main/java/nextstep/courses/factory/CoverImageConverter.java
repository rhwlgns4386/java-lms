package nextstep.courses.factory;

import nextstep.courses.domain.CoverImage;
import nextstep.courses.domain.ImageType;

public class CoverImageConverter {
    private CoverImageConverter() {
    }

    public static CoverImage toImage(String fileName, int width, int height, int size, ImageType imageType) {
        return new CoverImage(fileName, width, height, size, imageType);
    }
}
