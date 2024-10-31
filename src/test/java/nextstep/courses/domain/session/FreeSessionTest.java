package nextstep.courses.domain.session;

import nextstep.courses.domain.cover.CoverImage;
import nextstep.courses.domain.cover.CoverImageFile;
import nextstep.courses.domain.cover.CoverImageSize;
import nextstep.courses.domain.cover.CoverImageType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


class FreeSessionTest {

    private CoverImage coverImage;

    @BeforeEach
    void setUp() {
        coverImage = new CoverImage(1L, new CoverImageFile(30), CoverImageType.GIF, new CoverImageSize(300, 200));
    }

    @DisplayName("무료강의는 최대 수강 인원 제한이 없다.")
    @Test
    void register() {
        LocalDate startDate = LocalDate.of(2024, 10, 10);
        LocalDate endDate = LocalDate.of(2024, 10, 19);
        FreeSession freeCourse = new FreeSession(SessionStatus.OPEN, new SessionPeriod(startDate, endDate), coverImage);

        assertDoesNotThrow(() -> freeCourse.register());
    }
}
