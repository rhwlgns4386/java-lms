package nextstep.courses.service;

import nextstep.courses.domain.image.*;
import nextstep.courses.domain.session.*;
import nextstep.courses.domain.student.StudentRepository;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    private Session session;
    private Image image;
    private SessionDate sessionDate;
    private SessionCapacity sessionCapacity;
    private String title = "TDD";
    private Money fee = new Money(200_000L);

    @BeforeEach
    void init() {
        image = new Image(new ImageSize(1024), ImageType.GIF, new ImagePixel(300, 200));

        LocalDateTime start = LocalDateTime.of(2024, 10, 10, 10, 10);
        LocalDateTime end = LocalDateTime.of(2024, 10, 10, 10, 11);
        sessionDate = new SessionDate(start, end);
        sessionCapacity = new SessionCapacity(10);

        session = new PaidSession(title, image, sessionDate, sessionCapacity, fee);
    }

    @Test
    void createSessionTest() {
        Long courseId = 1L;
        long sessionId = sessionService.create(courseId, session, session.getImage());

        verify(sessionRepository).save(session, courseId);
        verify(imageRepository).save(session.getImage(), sessionId);
    }

    @Test
    void registerTest() {
        Long sessionId = 2L;
        NsUser nsUser = NsUserTest.JAVAJIGI;
        Payment payment = new Payment("1234", sessionId, nsUser.getId(), 200_000L);
        PaidSession paidSession = new PaidSession(sessionId, title, image, sessionDate, sessionCapacity, fee);
        paidSession.open();

        when(sessionRepository.findById(sessionId)).thenReturn(paidSession);

        Session registerSession = sessionService.register(sessionId, nsUser, payment);

        verify(studentRepository).saveAll(registerSession.getStudents(), sessionId);
    }
}
