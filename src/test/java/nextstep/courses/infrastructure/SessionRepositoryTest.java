//package nextstep.courses.infrastructure;
//
//import nextstep.courses.domain.SessionRepository;
//import nextstep.courses.domain.cover.CoverImage;
//import nextstep.courses.domain.cover.CoverImageFile;
//import nextstep.courses.domain.cover.CoverImageSize;
//import nextstep.courses.domain.cover.CoverImageType;
//import nextstep.courses.domain.session.*;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@JdbcTest
//class SessionRepositoryTest {
//    private static final Logger LOGGER = LoggerFactory.getLogger(SessionRepositoryTest.class);
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    private SessionRepository sessionRepository;
//    private CoverImage coverImage;
//    private SessionPeriod period;
//    private Long coverImageId;
//
//    @BeforeEach
//    void setUp() {
//        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
//        period = new SessionPeriod(LocalDate.of(2024, 10, 10), LocalDate.of(2024, 10, 19));
//        coverImage = new CoverImage(
//                1L,
//                new CoverImageFile(1024 * 100),
//                CoverImageType.JPG,
//                new CoverImageSize(600, 400)
//        );
//
//        jdbcTemplate.update(
//                "insert into cover_image (id, file_size, image_type, width, height, created_at) values (?, ?, ?, ?, ?, ?)",
//                1L,
//                coverImage.getFile().getSize(),
//                coverImage.getType().name(),
//                coverImage.getImageSize().getWidth(),
//                coverImage.getImageSize().getHeight(),
//                LocalDateTime.now()
//        );
//        coverImageId = 1L;
//    }
//
//    @DisplayName("무료 세션을 저장한다")
//    @Test
//    void saveFreeSession() {
//        // given
//        FreeSession freeSession = new FreeSession(SessionStatus.OPEN, period, coverImage);
//
//        // when
//        Long result = sessionRepository.saveFreeSession(freeSession);
//
//        // then
//        assertThat(result).isEqualTo(1);
//    }
//
//    @DisplayName("유료 세션을 저장한다")
//    @Test
//    void savePaidSession() {
//        // given
//        SessionCapacity capacity = new SessionCapacity(30);
//        Money courseFee = new Money(1000);
//        PaidSession paidSession = new PaidSession(SessionStatus.OPEN, period, capacity, courseFee, coverImage);
//
//        // when
//        Long result = sessionRepository.savePaidSession(paidSession);
//
//        // then
//        assertThat(result).isEqualTo(1);
//    }
//
//    @AfterEach
//    void tearDown() {
//        jdbcTemplate.update("delete from session");
//        jdbcTemplate.update("delete from cover_image");
//    }
//}
