package nextstep.courses.domain.SessionImage;

import java.util.List;

public class ImageType {
    private final String imageType;
    private final List<String> imageTypeList = List.of("gif","jpg","jpeg","png","svg");

    public ImageType(String imageType) {
        validateType(imageType);
        this.imageType = imageType;
    }

    private void validateType(String imageType) {
        if (!imageTypeList.contains(imageType)) {
            throw new IllegalArgumentException("이미지 타입이 올바르지 않습니다.");
        }
    }
}
