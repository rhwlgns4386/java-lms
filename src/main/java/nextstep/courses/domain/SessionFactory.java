package nextstep.courses.domain;

public class SessionFactory {

    public static Session createSession(SessionInfo sessionInfo, SessionImages sessionImages, SessionPrice sessionPrice, int studentMaxCount, SessionType sessionType) {
        if (SessionType.PAID == sessionType) {
            return new PaidSession(sessionInfo, sessionImages, sessionPrice, studentMaxCount);
        }

        return new FreeSession(sessionInfo, sessionImages);
    }
}

