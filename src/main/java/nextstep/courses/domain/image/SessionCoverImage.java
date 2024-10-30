package nextstep.courses.domain.image;


import java.util.Objects;

public class SessionCoverImage {
    private final Long id;
    private final CoverImageVolume coverImageVolume;
    private final CoverImageExtensionType coverImageExtensionType;
    private final CoverImageFileSize coverImageFileSize;

    public SessionCoverImage(long id, int volume, String fileName, int width, int height) {
        this(id, new CoverImageVolume(volume), CoverImageExtensionType.valueOfExtension(fileName), new CoverImageFileSize(width, height));
    }

    public SessionCoverImage(long id, CoverImageVolume coverImageVolume, CoverImageExtensionType coverImageExtensionType, CoverImageFileSize coverImageFileSize) {
        this.id = id;
        this.coverImageVolume = coverImageVolume;
        this.coverImageExtensionType = coverImageExtensionType;
        this.coverImageFileSize = coverImageFileSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SessionCoverImage that = (SessionCoverImage) o;
        return Objects.equals(id, that.id) && Objects.equals(coverImageVolume, that.coverImageVolume) && coverImageExtensionType == that.coverImageExtensionType && Objects.equals(coverImageFileSize, that.coverImageFileSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, coverImageVolume, coverImageExtensionType, coverImageFileSize);
    }
}
