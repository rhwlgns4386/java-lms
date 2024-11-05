package nextstep.courses.domain;

public class SessionMetaData {
    private final String title;

    private final String creatorId;

    public SessionMetaData(final String title, final String creatorId) {
        this.title = title;
        this.creatorId = creatorId;
    }

    public String getTitle() {
        return title;
    }

    public String getCreatorId() {
        return creatorId;
    }
}
