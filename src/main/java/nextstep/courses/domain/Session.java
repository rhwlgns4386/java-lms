package nextstep.courses.domain;


import java.awt.*;

public class Session {
    private static final int MAX_STUDENT_COUNT = 100;
    private Long id;
    private final String name;
    private final String description;
    private final CoverImage image;
    private final boolean isFree;
    private final int sessionFee;
    private final SessionState state;
    private final String startDate;
    private final String endDate;


    public Session(Long id, String name, String description, CoverImage image, boolean isFree, int sessionFee, String startDate, String endDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.isFree = isFree;
        this.sessionFee = sessionFee;
        this.state = SessionState.READY;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public boolean isFree() {
        return isFree;
    }

    public int getSessionFee() {
        return sessionFee;
    }
}
