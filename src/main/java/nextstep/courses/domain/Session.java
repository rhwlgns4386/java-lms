package nextstep.courses.domain;


import nextstep.courses.domain.Image.CoverImage;

public class Session {
    private static final int MAX_STUDENT_COUNT = 100;
    private Long id;
    private final String name;
    private final String description;
    private final CoverImage image;
    private final SessionState state;
    private final SessionDetail sessionDetail;
    private final SessionDate sessionDate;


    public Session(Long id, String name, String description, CoverImage image, boolean isFree, int sessionFee, String startDate, String endDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.sessionDetail = new SessionDetail(isFree, MAX_STUDENT_COUNT, sessionFee);
        this.state = SessionState.READY;
        this.sessionDate = new SessionDate(startDate, endDate);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
