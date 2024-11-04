package nextstep.courses.domain;

import nextstep.courses.domain.image.Image;
import nextstep.courses.domain.image.ImagePixel;
import nextstep.courses.domain.image.ImageSize;
import nextstep.courses.domain.image.ImageType;
import nextstep.courses.domain.session.FreeSession;
import nextstep.courses.domain.session.LegacySessionStatus;
import nextstep.courses.domain.session.SessionDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FreeSessionTest {
    private List<Image> images;
    private SessionDate sessionDate;
    private Long id;
    private String title;

    @BeforeEach
    void init() {
        Image newImage = new Image(1L, new ImageSize(1024), ImageType.GIF, new ImagePixel(300, 200));
        this.images = new ArrayList<>(List.of(newImage));

        LocalDateTime start = LocalDateTime.of(2024, 10, 10, 10, 10);
        LocalDateTime end = LocalDateTime.of(2024, 10, 10, 10, 11);

        this.sessionDate = new SessionDate(start, end);
        this.id = 1L;
        this.title = "TDD";
    }

    @Test
    @DisplayName("FreeSession 생성")
    void createFreeSessionTest() {
        FreeSession freeSession = new FreeSession(id, title, sessionDate, images);

        Assertions.assertThat(freeSession).isNotNull();
        Assertions.assertThat(freeSession.getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("FreeSession 수강 신청")
    void registerFreeSessionTest() {
        FreeSession freeSession = new FreeSession(id, title, sessionDate, images);

        freeSession.open();

        freeSession.register(RegistrationTest.REGISTRATION);

        Assertions.assertThat(freeSession.getId()).isEqualTo(id);
        Assertions.assertThat(freeSession.getSessionStatus()).isEqualTo(LegacySessionStatus.RECRUITING);
    }

    @Test
    @DisplayName("FreeSession 수강 신청 - 모집중이 아닌 강좌 신청 체크")
    void checkRegisterNotOpenFreeSessionTest() {
        FreeSession freeSession = new FreeSession(id, title, sessionDate, images);

        Assertions.assertThatThrownBy(() -> freeSession.register(RegistrationTest.REGISTRATION))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("강의 모집중")
    void openFreeSessionTest() {
        FreeSession freeSession = new FreeSession(id, title, sessionDate, images);

        freeSession.open();

        Assertions.assertThat(freeSession.getSessionStatus()).isEqualTo(LegacySessionStatus.RECRUITING);
    }

    @Test
    @DisplayName("강의 종료")
    void closeFreeSessionTest() {
        FreeSession freeSession = new FreeSession(id, title, sessionDate, images);

        freeSession.close();

        Assertions.assertThat(freeSession.getSessionStatus()).isEqualTo(LegacySessionStatus.CLOSE);
    }
}
