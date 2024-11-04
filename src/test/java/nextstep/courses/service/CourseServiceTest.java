package nextstep.courses.service;

import nextstep.courses.domain.course.Course;
import nextstep.courses.domain.course.CourseRepository;
import nextstep.courses.domain.image.Image;
import nextstep.courses.domain.image.ImagePixel;
import nextstep.courses.domain.image.ImageSize;
import nextstep.courses.domain.image.ImageType;
import nextstep.courses.domain.session.FreeSession;
import nextstep.courses.domain.session.SessionDate;
import nextstep.users.domain.NsUserTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {
    @InjectMocks
    CourseService courseService;
    @Mock
    CourseRepository courseRepository;
    @Mock
    SessionService sessionService;

    private FreeSession freeSession;

    @BeforeEach
    void init() {
        Image image = new Image(new ImageSize(1024), ImageType.GIF, new ImagePixel(300, 200));
        SessionDate sessionDate = new SessionDate(LocalDateTime.of(2024, 10, 10, 10, 10), LocalDateTime.of(2024, 10, 10, 10, 11));
        String title = "TDD";
        freeSession = new FreeSession(title, sessionDate, new ArrayList<>(List.of(image)));
    }

    @Test
    void createTest() {
        Course course = new Course("TDD, 클린 코드 with Java", NsUserTest.SANJIGI.getId());

        long courseId = courseService.create(course, freeSession);

        verify(courseRepository).save(course);
        verify(sessionService).create(courseId, freeSession, freeSession.getImages());
    }

    @Test
    void findByCourseIdTest() {
        Course course = new Course(1L, 1L, "TDD, 클린 코드 with Java", NsUserTest.SANJIGI.getId());

        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        when(sessionService.findAllByCourseId(course.getId())).thenReturn(List.of(freeSession));

        Course foundCourse = courseService.findById(course.getId());

        Assertions.assertThat(foundCourse).isNotNull();
        Assertions.assertThat(foundCourse.getId()).isEqualTo(course.getId());
        Assertions.assertThat(foundCourse.getSessions()).hasSameElementsAs(List.of(freeSession));
    }
}
