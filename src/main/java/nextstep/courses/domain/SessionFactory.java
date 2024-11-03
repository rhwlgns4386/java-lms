package nextstep.courses.domain;

public class SessionFactory {

    public static Session createSession(SessionInfo sessionInfo, SessionImage sessionImage,
                                        long salePrice, StateCode stateCode, SessionType sessionType) {
        return createSession(sessionInfo, sessionImage, salePrice, stateCode, 0, sessionType);
    }

    public static Session createSession(SessionInfo sessionInfo, SessionImage sessionImage,
                                        long salePrice, StateCode stateCode, int studentMaxCount, SessionType sessionType) {
        if (SessionType.PAID == sessionType) {
            return new PaidSession(sessionInfo, sessionImage, salePrice, stateCode, studentMaxCount);
        }

        return new FreeSession(sessionInfo, sessionImage, salePrice, stateCode);
    }
}

