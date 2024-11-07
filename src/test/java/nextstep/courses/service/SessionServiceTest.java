package nextstep.courses.service;

import nextstep.courses.domain.SessionApplyRepository;
import nextstep.courses.domain.SessionCoverImageRepository;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.SessionStudentRepository;
import nextstep.courses.dto.MultipartFile;
import nextstep.courses.dto.SessionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private SessionCoverImageRepository sessionCoverImageRepository;

    @Mock
    private SessionStudentRepository sessionStudentRepository;

    @Mock
    private SessionApplyRepository sessionApplyRepository;


    @InjectMocks
    private SessionService sessionService;

    private SessionDto paidSessionDto;
    private SessionDto freeSessionDto;

    @BeforeEach
    public void setUp() throws Exception {
        LocalDateTime startDate = LocalDateTime.of(2024, 10, 28, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 10, 30, 23, 59);

        paidSessionDto = new SessionDto(1L, "PAID", startDate, endDate, 1, 2000L);
        paidSessionDto.setMultipartFiles(List.of(
                new MultipartFile("a.jpg", 300, 200, 1 * 1024 * 1024L)
        ));

        freeSessionDto = new SessionDto(1L, "FREE", startDate, endDate, 0, 0L);
        freeSessionDto.setMultipartFiles(List.of(
                new MultipartFile("a.jpg", 300, 200, 1 * 1024 * 1024L),
                new MultipartFile("b.jpg", 300, 200, 1 * 1024 * 1024L)
        ));
    }

    void 이미지_1장() {
        SessionDto freeSessionDto = this.freeSessionDto;
        sessionService.saveSession(freeSessionDto);
        
    }
}
