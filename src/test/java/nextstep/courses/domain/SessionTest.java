package nextstep.courses.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class SessionTest {
    @Test
    @DisplayName("강의 class 생성")
    void createSessionTest() {
        Long id = 1L;
        String name = "session1";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(1);
        SessionType type = SessionType.FREE;
        SessionStatus status = SessionStatus.PREPARING;
        Image image = new Image(1L, 1000L, ImageType.GIF, 300L, 200L);
        Long price = 400_000L;
        Long students = Long.MAX_VALUE;

        Session session = Session.createFree(name, startDate, endDate, image);

        Assertions.assertThat(session).isNotNull();
    }

    @Test
    @DisplayName("무료 강의 생성")
    void createFreeSessionTest() {
        String name = "session1";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(1);
        Image image = new Image(1L, 1000L, ImageType.GIF, 300L, 200L);
        Long students = Long.MAX_VALUE;

        Session session = Session.createFree(name, startDate, endDate, image);

        Assertions.assertThat(session).isNotNull();
        Assertions.assertThat(session.getSessionType()).isEqualTo(SessionType.FREE);
        Assertions.assertThat(session.getSessionStatus()).isEqualTo(SessionStatus.PREPARING);
        Assertions.assertThat(session.getPrice()).isEqualTo(0);
        Assertions.assertThat(session.getCapacity()).isEqualTo(students);
    }

    @Test
    @DisplayName("유료 강의 생성")
    void createPaidSessionTest() {
        String name = "session1";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(1);
        Image image = new Image(1L, 1000L, ImageType.GIF, 300L, 200L);
        Long price = 400_000L;
        Long students = 20L;

        Session session = Session.createPaid(name, startDate, endDate, image, price, students);

        Assertions.assertThat(session).isNotNull();
        Assertions.assertThat(session.getSessionType()).isEqualTo(SessionType.PAID);
        Assertions.assertThat(session.getSessionStatus()).isEqualTo(SessionStatus.PREPARING);
        Assertions.assertThat(session.getPrice()).isEqualTo(price);
        Assertions.assertThat(session.getCapacity()).isEqualTo(students);
    }


    @Test
    @DisplayName("유료 강의 생성 - 강의 금액 예외")
    void createPaidSessionTest_ZeroPrice() {
        String name = "session1";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(1);
        Image image = new Image(1L, 1000L, ImageType.GIF, 300L, 200L);
        Long price = 0L;
        Long students = 20L;

        Assertions.assertThatThrownBy(() -> Session.createPaid(name, startDate, endDate, image, price, students))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("유료 강의 생성 - 승강 신청 예외")
    void createPaidSessionTest_ZeroStudents() {
        String name = "session1";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(1);
        Image image = new Image(1L, 1000L, ImageType.GIF, 300L, 200L);
        Long price = 100_000L;
        Long students = 0L;

        Assertions.assertThatThrownBy(() -> Session.createPaid(name, startDate, endDate, image, price, students))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("강의 삭제")
    void deleteSessionTest() {
        String name = "session1";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(1);
        Image image = new Image(1L, 1000L, ImageType.GIF, 300L, 200L);
        Long price = 400_000L;
        Long students = 20L;

        Session session = Session.createPaid(name, startDate, endDate, image, price, students);

        session.delete();

        Assertions.assertThat(session.getDeleted()).isTrue();
    }
}
