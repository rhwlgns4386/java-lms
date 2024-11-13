package nextstep.session.domain.fixture;

import java.time.LocalDate;
import java.util.List;

import nextstep.session.domain.CoverImage;
import nextstep.session.domain.Session;

public class FixtureSessionFactory {

    private FixtureSessionFactory() {
    }

    public static Session createPaidSession(Long id, Long studentCapacity, Long sessionFee) {
        return Session.createPaidSession(
            id,
            "title",
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 12, 1),
            null,
            studentCapacity,
            sessionFee);
    }

    public static Session createFreeSession(Long id) {
        return Session.createFreeSession(
            id,
            "title",
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 12, 1),
            null);
    }

    public static Session createFreeSession(Long id, List<CoverImage> coverImages) {
        return Session.createFreeSession(
            id,
            "title",
            LocalDate.of(2024, 1, 1),
            LocalDate.of(2024, 12, 1),
            coverImages);
    }
}
