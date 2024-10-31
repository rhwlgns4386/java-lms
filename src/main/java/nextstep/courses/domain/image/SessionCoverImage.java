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

    public SessionCoverImage(SessionCoverImageBuilder sessionCoverImageBuilder) {
        this.id = sessionCoverImageBuilder.id;
        this.coverImageVolume = new CoverImageVolume(sessionCoverImageBuilder.volume);
        this.coverImageExtensionType = CoverImageExtensionType.valueOfExtension(sessionCoverImageBuilder.fileName);
        this.coverImageFileSize = new CoverImageFileSize(sessionCoverImageBuilder.width, sessionCoverImageBuilder.height);
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

    public static class SessionCoverImageBuilder {
        private Long id;
        private int volume;
        private String fileName;
        private int width;
        private int height;

        public SessionCoverImageBuilder(Long id) {
            this.id = id;
        }

        public SessionCoverImageBuilder volume(int volume) {
            this.volume = volume;
            return this;
        }

        public SessionCoverImageBuilder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public SessionCoverImageBuilder width(int width) {
            this.width = width;
            return this;
        }

        public SessionCoverImageBuilder height(int height) {
            this.height = height;
            return this;
        }

        public SessionCoverImage build() {
            return new SessionCoverImage(this);
        }

    }

}
