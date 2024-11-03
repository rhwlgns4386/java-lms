package nextstep.courses.domain.session;

import nextstep.courses.domain.cover.CoverImage;

public class SessionFactory {
    private SessionFactory() {
    }

    public static DefaultSession createSession(String sessionType, Long id, Status status, Period period,
                                               CoverImage coverImage, Money courseFee, Capacity capacity) {
        if (SessionType.PAID.getCode().equals(sessionType)) {
            return new PaidSession(id, status, period, coverImage, courseFee, capacity);
        }
        return new FreeSession(id, status, period, coverImage, capacity);
    }
}
