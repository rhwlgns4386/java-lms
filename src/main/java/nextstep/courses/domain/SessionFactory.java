package nextstep.courses.domain;

public class SessionFactory {


    public static Session createSession(SessionInfo sessionInfo, SessionImages sessionImages, SessionPrice sessionPrice, int studentMaxCount, SessionType sessionType) {
        return createSession(null, sessionInfo, sessionImages, sessionPrice, studentMaxCount, sessionType);
    }

    public static Session createSession(SessionId sessionId, SessionInfo sessionInfo, SessionImages sessionImages, SessionPrice sessionPrice, int studentMaxCount, SessionType sessionType) {
        if (SessionType.PAID == sessionType) {
            return new PaidSession(sessionId, sessionInfo, sessionImages, sessionPrice, studentMaxCount);
        }

        return new FreeSession(sessionId, sessionInfo, sessionImages);
    }
}

