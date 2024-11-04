package nextstep.courses.domain.session;

import nextstep.courses.domain.cover.CoverImage;
import nextstep.courses.domain.cover.CoverImageFile;
import nextstep.courses.domain.cover.CoverImageSize;
import nextstep.courses.domain.cover.CoverImageType;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class PaidSessionTest {

    private CoverImage coverImage;
    private Period period;

    @BeforeEach
    void setUp() {
        coverImage = new CoverImage(1L, new CoverImageFile(30), CoverImageType.GIF, new CoverImageSize(300, 200));
        period = new Period(LocalDate.of(2024, 10, 10), LocalDate.of(2024, 10, 10).plusDays(30));
    }

    @DisplayName("수강신청을 성공한다.")
    @Test
    void register() {
        Money courseFee = new Money(10000);
        Capacity capacity = new Capacity(10);
        PaidSession course = new PaidSession(SessionStatus.ready().recruiting(), period, List.of(coverImage), courseFee, capacity);
        Payment payment = new Payment("PG1", 1L, 1L, 10000L);

        course.register(NsUserTest.GREEN, payment);

        assertAll(
                () -> assertThat(course.getRegistrations())
                        .extracting("sessionId", "maxStudents")
                        .containsExactly(course.getId(), 10),
                () -> assertThat(course.getRegistrations().contains(NsUserTest.GREEN.getId())).isTrue()
        );
    }

    @DisplayName("모집 상태가 아닌 강의는 수강신청을 할 수 없다.")
    @Test
    void register_NotOpenStatus() {
        Capacity capacity = new Capacity(10);
        Money courseFee = new Money(10000);
        PaidSession paidCourse = new PaidSession(SessionStatus.ready().notRecruiting(), period, List.of(coverImage), courseFee, capacity);
        Payment payment = new Payment("PG1", 1L, 1L, 10000L);

        assertThatThrownBy(() -> paidCourse.register(NsUserTest.GREEN, payment))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("최대 수강 인원을 초과한 강의에 등록하는 경우 예외로 처리한다.")
    @Test
    void register_FullCapacity() {
        Capacity capacity = new Capacity(1);
        Money courseFee = new Money(10000);
        PaidSession paidCourse = new PaidSession(SessionStatus.ready().recruiting(), period, List.of(coverImage), courseFee, capacity);
        Payment payment = new Payment("PG1", 1L, 1L, 10000L);

        paidCourse.register(NsUserTest.GREEN, payment);

        assertThatThrownBy(() -> paidCourse.register(NsUserTest.JAVAJIGI, payment))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("결제금액과 수강료가 일치하지 않으면 예외로 처리한다.")
    @Test
    void register_InvalidPaymentAmount() {
        Capacity capacity = new Capacity(10);
        Money courseFee = new Money(10000);
        PaidSession paidCourse = new PaidSession(SessionStatus.ready().recruiting(), period, List.of(coverImage), courseFee, capacity);
        Payment payment = new Payment("PG1", 1L, 1L, 9000L);

        assertThatThrownBy(() -> paidCourse.register(NsUserTest.GREEN, payment))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("결제 정보가 없을 경우 예외로 처리한다.")
    @Test
    void register_NullPayment() {
        Capacity capacity = new Capacity(10);
        Money courseFee = new Money(10000);
        PaidSession paidCourse = new PaidSession(SessionStatus.ready().recruiting(), period, List.of(coverImage), courseFee, capacity);

        assertThatThrownBy(() -> paidCourse.register(NsUserTest.GREEN, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("여러 개의 커버 이미지를 가진 세션을 생성할 수 있다")
    @Test
    void createWithMultipleImages() {
        // given
        Money courseFee = new Money(10000);
        Capacity capacity = new Capacity(10);

        CoverImage secondImage = new CoverImage(
                2L,
                new CoverImageFile(1024 * 100),
                CoverImageType.JPG,
                new CoverImageSize(300, 200)
        );
        List<CoverImage> images = List.of(coverImage, secondImage);

        // when
        PaidSession session = new PaidSession(SessionStatus.ready().recruiting(), period, images, courseFee, capacity);

        // then
        assertAll(
                () -> assertThat(session.getCoverImages()).hasSize(2),
                () -> assertThat(session.getCoverImage()).isEqualTo(coverImage),
                () -> assertThat(session.getCoverImages())
                        .containsExactly(coverImage, secondImage)
        );
    }

}
