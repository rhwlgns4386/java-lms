package nextstep.courses.domain.session;

import java.time.LocalDateTime;

public class SessionDuration {

    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public SessionDuration(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
