package nextstep.courses.domain.image;


import java.util.Objects;

public class SessionCoverImage {
    private final Long id;
    private final Long sessionId;
    private final String fileName;
    private final CoverImageVolume coverImageVolume;
    private final CoverImageExtensionType coverImageExtensionType;
    private final CoverImageFileSize coverImageFileSize;
    private final String filePath;

    public SessionCoverImage(Long id, Long sessionId, int volume, String fileName,int width, int height, String filePath) {
        this(id, sessionId, fileName, new CoverImageVolume(volume), CoverImageExtensionType.valueOfExtension(fileName), new CoverImageFileSize(width, height), filePath);
    }

    public SessionCoverImage(long id, Long sessionId, String fileName, CoverImageVolume coverImageVolume, CoverImageExtensionType coverImageExtensionType, CoverImageFileSize coverImageFileSize, String filePath) {
        this.id = id;
        this.sessionId = sessionId;
        this.fileName = fileName;
        this.coverImageVolume = coverImageVolume;
        this.coverImageExtensionType = coverImageExtensionType;
        this.coverImageFileSize = coverImageFileSize;
        this.filePath = filePath;
    }

    public SessionCoverImage(SessionCoverImageBuilder sessionCoverImageBuilder) {
        this.id = sessionCoverImageBuilder.id;
        this.coverImageVolume = new CoverImageVolume(sessionCoverImageBuilder.volume);
        this.coverImageExtensionType = CoverImageExtensionType.valueOfExtension(sessionCoverImageBuilder.fileName);
        this.coverImageFileSize = new CoverImageFileSize(sessionCoverImageBuilder.width, sessionCoverImageBuilder.height);
        this.sessionId = sessionCoverImageBuilder.sessionId;
        this.fileName = sessionCoverImageBuilder.fileName;
        this.filePath = sessionCoverImageBuilder.filePath;
    }

    public Long getId() {
        return id;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public CoverImageVolume getCoverImageVolume() {
        return coverImageVolume;
    }

    public CoverImageExtensionType getCoverImageExtensionType() {
        return coverImageExtensionType;
    }

    public CoverImageFileSize getCoverImageFileSize() {
        return coverImageFileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
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
        private Long sessionId;
        private int volume;
        private String fileName;
        private int width;
        private int height;
        private String filePath;

        public SessionCoverImageBuilder() {

        }

        public SessionCoverImageBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public SessionCoverImageBuilder sessionId(Long sessionId) {
            this.sessionId = sessionId;
            return this;
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

        public SessionCoverImageBuilder filePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public SessionCoverImage build() {
            return new SessionCoverImage(this);
        }

    }

}
