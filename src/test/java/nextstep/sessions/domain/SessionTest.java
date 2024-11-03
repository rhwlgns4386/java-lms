package nextstep.sessions.domain;

import nextstep.sessions.Image.CoverImage;
import nextstep.sessions.Session;
import nextstep.sessions.Session.SessionBuilder;
import nextstep.sessions.SessionState;
import nextstep.users.domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Session 도메인 테스트")
public class SessionTest {

    private Session session;
    private Student student;

    @BeforeEach
    public void setUp() {
        session = new SessionBuilder(1L, "테스트세션", "테스트 강의", null, LocalDateTime.parse("2024-01-01T00:00:00"), LocalDateTime.parse("2024-01-02T00:00:00"))
                .isFree(false)
                .maxStudentCount(50)
                .sessionFee(10000L)
                .state(SessionState.READY)
                .build();

        student = new Student(1L, "John Doe", "1234", "Doe", "johndoe@test.com");
    }

    @Test
    @DisplayName("세션이 정상적으로 생성되었는지 확인")
    public void 강의_생성() {
        assertThat(session.getId()).isEqualTo(1L);
        assertThat(session.getName()).isEqualTo("테스트세션");
        assertThat(session.getDescription()).isEqualTo("테스트 강의");
    }

    @Test
    @DisplayName("세션 시작일과 종료일이 올바른지 확인")
    public void 강의_날짜_확인() {
        assertThat(session.getStartDate()).isEqualTo(LocalDateTime.parse("2024-01-01T00:00:00"));
        assertThat(session.getEndDate()).isEqualTo(LocalDateTime.parse("2024-01-02T00:00:00"));
    }

    @Test
    @DisplayName("세션 상태가 닫힘 상태일 때 등록에 실패하는지 확인")
    public void 강의_등록_실패_접수_기간_아님() {
        session = new SessionBuilder(2L, "테스트세션", "테스트 강의", null, LocalDateTime.parse("2024-01-01T00:00:00"), LocalDateTime.parse("2024-01-02T00:00:00"))
                .state(SessionState.CLOSE)
                .build();

        assertThatThrownBy(() -> session.register(student, 10000L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(Session.SESSION_NOT_OPEN_MESSAGE);
    }

    @Test
    @DisplayName("유료 세션 설정 확인")
    public void 유료_강의_설정() {
        Session paidSession = new SessionBuilder(3L, "유료세션", "유료 강의", null, LocalDateTime.parse("2024-06-01T00:00:00"), LocalDateTime.parse("2024-06-10T00:00:00"))
                .isFree(false)
                .sessionFee(50000L)
                .build();

        assertThat(paidSession.getDescription()).isEqualTo("유료 강의");
    }

    @Test
    @DisplayName("무료 세션 설정 확인")
    public void 무료_강의_설정() {
        Session freeSession = new SessionBuilder(4L, "무료세션", "무료 강의", null, LocalDateTime.parse("2024-06-01T00:00:00"), LocalDateTime.parse("2024-06-10T00:00:00"))
                .isFree(true)
                .build();

        assertThat(freeSession.getDescription()).isEqualTo("무료 강의");
    }
}
