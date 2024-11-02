package nextstep.fixture;

import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.SessionStatus;

import java.util.ArrayList;
import java.util.List;

import static nextstep.courses.domain.SessionStatus.RECRUITING;

public class PaidSessionCreator {

    public static PaidSession status(SessionStatus status) {
        return new PaidSession(1L, SessionDateCreator.standard(), SessionImageCreator.standard(), status, new ArrayList<>(), 80, 25000);
    }

    public static PaidSession capacity(List<Long> student, int maxNumOfStudents) {
        return new PaidSession(1L, SessionDateCreator.standard(), SessionImageCreator.standard(), RECRUITING, student, maxNumOfStudents, 25000);
    }

    public static PaidSession fee(int sessionFee) {
        return new PaidSession(1L, SessionDateCreator.standard(), SessionImageCreator.standard(), RECRUITING, new ArrayList<>(), 80, sessionFee);
    }

    public static PaidSession user(Long userId) {
        return new PaidSession(1L, SessionDateCreator.standard(), SessionImageCreator.standard(), RECRUITING, List.of(userId), 80, 25000);
    }
}
