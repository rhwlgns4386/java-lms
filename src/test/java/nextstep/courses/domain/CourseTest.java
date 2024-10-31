package nextstep.courses.domain;

import java.time.LocalDateTime;

public class CourseTest {
    public static final Course COURSE = new Course(1L, "Java 기초 강의", 1L, LocalDateTime.now(), LocalDateTime.now());
}
