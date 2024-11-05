package nextstep.courses.domain;

public class SessionFactory {

    public static Session createSession(SessionInfo sessionInfo, SessionImage sessionImage, SessionPrice sessionPrice, int studentMaxCount, SessionType sessionType) {
        if (SessionType.PAID == sessionType) {
            return new PaidSession(sessionInfo, sessionImage, sessionPrice, studentMaxCount);
        }

        return new FreeSession(sessionInfo, sessionImage);
    }
}

