package nextstep.courses.domain;

public class CoverImage {
    private int size;
    private ImageType type;
    private int width;
    private int height;

    public CoverImage(int size, String type, int width, int height) {
        validateSize();
        validateType();
        validateRatio();

        this.size = size;
        this.type = ImageType.valueOf(type);
        this.width = width;
        this.height = height;
    }

    private void validateRatio() {
        if (width < 300) {
            throw new IllegalArgumentException("이미지의 너비는 300보다 작을 수 없습니다.");
        }

        if (height < 200) {
            throw new IllegalArgumentException("이미지의 높이는 200보다 작을 수 없습니다.");
        }

        if (width / height != 3 / 2) {
            throw new IllegalArgumentException("이미지의 비율은 3:2이어야 합니다.");
        }
    }

    private void validateType() {
        if(!ImageType.contains(type)) {
            throw new IllegalArgumentException("지원하지 않는 이미지 타입입니다.");
        }
    }

    private void validateSize() {
        if(size > 1 * 1024 * 1024) {
            throw new IllegalArgumentException("이미지 크기는 1MB를 넘을 수 없습니다.");
        }
    }
}
