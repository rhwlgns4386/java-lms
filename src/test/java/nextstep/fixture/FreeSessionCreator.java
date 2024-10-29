package nextstep.fixture;

import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.SessionStatus;

import java.util.ArrayList;
import java.util.List;

import static nextstep.courses.domain.SessionDateTest.sd1;
import static nextstep.courses.domain.SessionImageTest.si1;

public class FreeSessionCreator {

    public static FreeSession standard() {
        return new FreeSession(1L, sd1, si1, SessionStatus.RECRUITING, new ArrayList<>());
    }

    public static FreeSession status(SessionStatus status) {
        return new FreeSession(1L, sd1, si1, status, new ArrayList<>());
    }

    public static FreeSession user(Long userId) {
        return new FreeSession(1L, sd1, si1, SessionStatus.RECRUITING, List.of(userId));
    }
}
