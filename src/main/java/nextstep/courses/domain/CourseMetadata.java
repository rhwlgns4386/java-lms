package nextstep.courses.domain;

public class CourseMetadata {
    public static final int MINIMUM_ID = 0;
    private final long id;
    private final String title;
    private final long creatorId;

    public CourseMetadata(long id, String title, long creatorId) {
        if (id < MINIMUM_ID) {
            throw new IllegalArgumentException("ID는 " + MINIMUM_ID + "이상이어야 합니다.");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("제목을 입력해주세요.");
        }
        if (creatorId < MINIMUM_ID) {
            throw new IllegalArgumentException("생성자의 아이디는" + MINIMUM_ID + "이상이어야 합니다.");
        }
        this.id = id;
        this.title = title;
        this.creatorId = creatorId;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getCreatorId() {
        return creatorId;
    }
}
