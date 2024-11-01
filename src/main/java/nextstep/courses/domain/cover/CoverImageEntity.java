package nextstep.courses.domain.cover;

import java.time.LocalDateTime;

public class CoverImageEntity {
    private Long id;
    private int fileSize;
    private String imageType;
    private int width;
    private int height;
    private LocalDateTime createdAt;

    public static CoverImageEntity from(CoverImage coverImage) {
        CoverImageEntity entity = new CoverImageEntity();
        entity.fileSize = coverImage.getFile().getSize();
        entity.imageType = coverImage.getType().name();
        entity.width = coverImage.getImageSize().getWidth();
        entity.height = coverImage.getImageSize().getHeight();
        entity.createdAt = LocalDateTime.now();
        return entity;
    }

    public CoverImage toDomain() {
        return new CoverImage(
                id,
                new CoverImageFile(fileSize),
                CoverImageType.valueOf(imageType),
                new CoverImageSize(width, height)
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
