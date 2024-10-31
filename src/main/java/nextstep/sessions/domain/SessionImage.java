package nextstep.sessions.domain;

public class SessionImage {
    private static final double MAX_SIZE = 1024 * 1024;
    private static final int MIN_WIDTH = 300;
    private static final int MIN_HEIGHT = 200;
    private static final double ASPECT_RATIO = 3.0 / 2.0;

    private Long id;

    private int size;

    private ImageType type;

    private double width;

    private double height;

    public SessionImage(Long id, int size, ImageType type, double width, double height) {
        validate(size, width, height);
        this.id = id;
        this.size = size;
        this.type = type;
        this.width = width;
        this.height = height;
    }

    public SessionImage(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type.name();
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void validate(int size, double width, double height) {
        validateSize(size);
        validateDimension(width, height);
    }

    private void validateSize(int size) {
        if (size > MAX_SIZE) {
            throw new IllegalArgumentException("size는 1024 * 1024보다 작아야 합니다.");
        }
    }

    private void validateDimension(double width, double height) {
        if (width < MIN_WIDTH || height < MIN_HEIGHT || (width / height) != ASPECT_RATIO) {
            throw new IllegalArgumentException("width는 0보다 커야 합니다.");
        }
    }
}
