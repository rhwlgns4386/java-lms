package nextstep.courses.domain;

import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class FreeSessionTest {
    private Image image;
    private SessionDate sessionDate;

    @BeforeEach
    void init() {
        this.image = new Image(1L, new ImageSize(1024), ImageType.GIF, new ImageProperty(300L, 200L));

        LocalDateTime start = LocalDateTime.of(2024, 10, 10, 10, 10);
        LocalDateTime end = LocalDateTime.of(2024, 10, 10, 10, 11);

        this.sessionDate = new SessionDate(start, end);
    }

    @Test
    @DisplayName("FreeSession 생성")
    void createFreeSessionTest() {
        SessionId sessionId = SessionId.of(1L, "TDD");
        FreeSession freeSession = new FreeSession(sessionId, sessionDate, image);

        Assertions.assertThat(freeSession).isNotNull();
        Assertions.assertThat(freeSession.getSessionId()).isEqualTo(sessionId);
    }

    @Test
    @DisplayName("수강생 추가")
    void addStudentsTest() {
        SessionId sessionId = SessionId.of(1L, "TDD");
        FreeSession freeSession = new FreeSession(sessionId, sessionDate, image);

        freeSession.addStudents(NsUserTest.SANJIGI);

        StudentManager studentManager = freeSession.getStudentManager();
        Assertions.assertThat(studentManager.getStudentCount()).isEqualTo(1);
        NsUser foundStudent = studentManager.findById(NsUserTest.SANJIGI.getId());
        Assertions.assertThat(foundStudent).isNotNull();
    }
}
