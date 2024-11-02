package nextstep.courses.service;

import nextstep.courses.domain.image.*;
import nextstep.courses.domain.session.*;
import nextstep.courses.domain.student.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {
    @InjectMocks
    SessionService sessionService;
    @Mock
    SessionRepository sessionRepository;
    @Mock
    ImageRepository imageRepository;
    @Mock
    StudentRepository studentRepository;

    Session session;

    @BeforeEach
    void init() {
        Image image = new Image(new ImageSize(1024), ImageType.GIF, new ImagePixel(300, 200));

        LocalDateTime start = LocalDateTime.of(2024, 10, 10, 10, 10);
        LocalDateTime end = LocalDateTime.of(2024, 10, 10, 10, 11);
        SessionDate sessionDate = new SessionDate(start, end);
        String title = "TDD";
        SessionCapacity sessionCapacity = new SessionCapacity(10);
        Money fee = new Money(200_000L);

        this.session = new PaidSession(title, image, sessionDate, sessionCapacity, fee);
    }

    @Test
    @DisplayName("강의 생성")
    void createSession() {
        Long courseId = 1L;
        long sessionId = sessionService.create(courseId, session, session.getImage());

        verify(sessionRepository).save(session, courseId);
        verify(imageRepository).save(session.getImage(), sessionId);
    }
}
