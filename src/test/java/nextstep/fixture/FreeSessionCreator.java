package nextstep.fixture;

import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.ProgressStatus;
import nextstep.courses.domain.RecruitingStatus;

import java.util.ArrayList;
import java.util.List;

public class FreeSessionCreator {

    public static FreeSession status(RecruitingStatus recruiting, ProgressStatus progressing) {
        return new FreeSession(1L, SessionDateCreator.standard(), SessionImageCreator.standard(), recruiting, progressing, new ArrayList<>());
    }

    public static FreeSession standard() {
        return new FreeSession(1L, SessionDateCreator.standard(), SessionImageCreator.standard(), RecruitingStatus.RECRUITING, new ArrayList<>());
    }

    public static FreeSession recruitingStatus(RecruitingStatus status) {
        return new FreeSession(1L, SessionDateCreator.standard(), SessionImageCreator.standard(), status, new ArrayList<>());
    }

    public static FreeSession user(Long userId) {
        return new FreeSession(1L, SessionDateCreator.standard(), SessionImageCreator.standard(), RecruitingStatus.RECRUITING, List.of(userId));
    }
}
