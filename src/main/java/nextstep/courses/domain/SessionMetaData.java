package nextstep.courses.domain;

public class SessionMetaData {
    private final String title;

    private final String creatorId;

    public SessionMetaData(final String title, final String creatorId) {
        if(title == null || title.isEmpty()) {
            throw new IllegalArgumentException("강의 제목을 입력해주세요.");
        }
        if(creatorId == null || creatorId.isEmpty()) {
            throw new IllegalArgumentException("작성자 id를 입력해주세요.");
        }
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
