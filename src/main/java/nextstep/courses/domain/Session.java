package nextstep.courses.domain;

import java.time.LocalDate;

public class Session {
    private Long id;

    private LocalDate startAt;

    private LocalDate endAt;

    private CoverImage image;

    private PaymentPolicy paymentPolicy;

    private CourseStatus courseStatus;

    private int studentCount;

    public Session(LocalDate startAt, LocalDate endAt, CoverImage image, PaymentPolicy paymentPolicy) {
        this.startAt = startAt;
        this.endAt = endAt;
        this.image = image;
        this.paymentPolicy = paymentPolicy;
        this.courseStatus = CourseStatus.PENDING;
    }

    public static Session create(LocalDate startAt, LocalDate endAt, CoverImage image, PaymentPolicy paymentPolicy) {
        return new Session(startAt, endAt, image, paymentPolicy);
    }

    public CourseStatus getCourseStatus() {
        return courseStatus;
    }

    public void open() {
        courseStatus = CourseStatus.OPEN;
    }
}
