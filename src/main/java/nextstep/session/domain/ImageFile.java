package nextstep.session.domain;

public class ImageFile {
    private final int size;
    private final int width;
    private final int height;
    private final String extension;

    public ImageFile(int size, int width, int height, String extension) {
        this.size = size;
        this.width = width;
        this.height = height;
        this.extension = extension;
    }

    public int getSize() {
        return size;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getExtension() {
        return extension;
    }
}
