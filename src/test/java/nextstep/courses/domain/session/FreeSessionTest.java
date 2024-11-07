package nextstep.courses.domain.session;

import nextstep.courses.domain.cover.CoverImage;
import nextstep.courses.domain.cover.CoverImageFile;
import nextstep.courses.domain.cover.CoverImageSize;
import nextstep.courses.domain.cover.CoverImageType;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


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
        FreeSession freeCourse = new FreeSession(SessionProgress.READY, SessionRecruitmentStatus.RECRUITING, new Period(startDate, endDate), coverImage);

        freeCourse.register(NsUserTest.GREEN);

        assertThat(freeCourse.getRegistrations())
                .hasSize(1)
                .extracting("sessionId", "userId")
                .containsExactly(
                        Tuple.tuple(freeCourse.getId(), NsUserTest.GREEN.getId())
                );
    }

    @DisplayName("이미 신청한 사용자가 수강신청을 하면 예외로 처리한다.")
    @Test
    void alreadyExistUser() {
        LocalDate startDate = LocalDate.of(2024, 10, 10);
        LocalDate endDate = LocalDate.of(2024, 10, 19);
        FreeSession freeCourse = new FreeSession(SessionProgress.READY, SessionRecruitmentStatus.RECRUITING, new Period(startDate, endDate), coverImage);

        freeCourse.register(NsUserTest.GREEN);

        assertThatThrownBy(() -> freeCourse.register(NsUserTest.GREEN))
                .isInstanceOf(IllegalArgumentException.class);

    }
}
