package nextstep.courses.dto;

public class MultipartFile {
    private String originalFileName;
    private int width;
    private int height;
    private Long size;

    public MultipartFile(String originalFileName, int width, int height, Long size) {
        this.originalFileName = originalFileName;
        this.width = width;
        this.height = height;
        this.size = size;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public Long getSize() {
        return size;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
