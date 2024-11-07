package nextstep.courses.domain;

public class SessionImageCreate {

    private final long fileSize;
    private final String fileType;
    private final int width;
    private final int height;

    public SessionImageCreate(long fileSize, String fileType, int width, int height) {
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.width = width;
        this.height = height;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
