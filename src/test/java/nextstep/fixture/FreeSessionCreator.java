package nextstep.fixture;

import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.SessionStatus;

import java.util.ArrayList;
import java.util.List;

public class FreeSessionCreator {

    public static FreeSession standard() {
        return new FreeSession(1L, SessionDateCreator.standard(), SessionImageCreator.standard(), SessionStatus.RECRUITING, new ArrayList<>());
    }

    public static FreeSession status(SessionStatus status) {
        return new FreeSession(1L, SessionDateCreator.standard(), SessionImageCreator.standard(), status, new ArrayList<>());
    }

    public static FreeSession user(Long userId) {
        return new FreeSession(1L, SessionDateCreator.standard(), SessionImageCreator.standard(), SessionStatus.RECRUITING, List.of(userId));
    }
}
