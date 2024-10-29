package nextstep.courses.domain;

import nextstep.courses.domain.cover.CoverImage;
import nextstep.courses.domain.cover.CoverImageFile;
import nextstep.courses.domain.cover.CoverImageSize;
import nextstep.courses.domain.cover.CoverImageType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class CourseTest {
    private CoverImage coverImage;

    @BeforeEach
    void setUp() {
        coverImage = new CoverImage(new CoverImageFile(30), CoverImageType.GIF, new CoverImageSize(300, 200));
    }

    @DisplayName("정상적인 값으로 과정을 생성할 수 있다.")
    @Test
    void createCourseWithValidValues() {
        CourseMetadata metadata = new CourseMetadata(1L, "자바 프로그래밍", 1L);
        Generation generation = new Generation(1);
        assertDoesNotThrow(() -> new Course(metadata, generation, new ArrayList<>(), new BaseTime()));
    }

    @DisplayName("과정에 세션을 추가할 수 있다.")
    @Test
    void addSession() {
        CourseMetadata metadata = new CourseMetadata(1L, "자바 프로그래밍", 1L);
        Generation generation = new Generation(1);
        Course course = new Course(metadata, generation, new ArrayList<>(), new BaseTime());

        LocalDate startDate = LocalDate.of(2024, 10, 10);
        LocalDate endDate = LocalDate.of(2024, 10, 19);
        SessionPeriod period = new SessionPeriod(startDate, endDate);
        DefaultSession freeSession = new FreeSession(SessionStatus.OPEN, period, coverImage);

        SessionCapacity capacity = new SessionCapacity(5, 10);
        Money courseFee = new Money(10000);
        DefaultSession paidSession = new PaidSession(SessionStatus.CLOSED, period, capacity, courseFee, coverImage);

        course.addSession(freeSession);
        course.addSession(paidSession);

        assertAll(
                () -> assertThat(course)
                        .extracting("courseMetadata.id", "generation.generation", "courseMetadata.title")
                        .containsExactly(1L, 1, "자바 프로그래밍"),
                () -> assertThat(course.getSessions())
                        .hasSize(2),
                () -> assertThat(course.getSessions())
                        .element(0)
                        .extracting("status", "period.startDate", "period.endDate", "coverImage")
                        .containsExactly(SessionStatus.OPEN, startDate, endDate, coverImage),
                () -> assertThat(course.getSessions())
                        .element(1)
                        .extracting("status", "period.startDate", "period.endDate")
                        .containsExactly(SessionStatus.CLOSED, startDate, endDate)
        );


    }
}
