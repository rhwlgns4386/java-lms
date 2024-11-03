package nextstep.courses.infrastructure;

import nextstep.courses.domain.cover.CoverImage;
import nextstep.courses.domain.cover.CoverImageFile;
import nextstep.courses.domain.cover.CoverImageSize;
import nextstep.courses.domain.cover.CoverImageType;
import nextstep.courses.domain.session.*;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class SessionRegistrationRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRegistrationRepository sessionRegistration;
    private FreeSession freeSession;
    private Long sessionId;

    @BeforeEach
    void setUp() {
        sessionRegistration = new JdbcSessionRegistrationRepository(jdbcTemplate);

        sessionId = 1L;

        CoverImage coverImage = new CoverImage(1L, new CoverImageFile(102400), CoverImageType.JPG, new CoverImageSize(600, 400));
        Period period = new Period(LocalDate.of(2024, 10, 10), LocalDate.of(2024, 10, 19));
        Capacity capacity = new Capacity(10);

        freeSession = new FreeSession(sessionId, Status.OPEN, period, coverImage, capacity);
    }

    @DisplayName("세션의 수강 신청 정보를 조회할 수 있다.")
    @Test
    void saveRegistrations() {
        // given
        freeSession.register(NsUserTest.GREEN);
        sessionRegistration.saveRegistrations(sessionId, freeSession.getRegisteredStudentIds());

        // when
        List<Long> savedUserIds = sessionRegistration.findRegisteredUserIds(sessionId);

        // then
        assertThat(savedUserIds).containsExactly(NsUserTest.GREEN.getId());
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.update("DELETE FROM session_registration");
        jdbcTemplate.update("DELETE FROM session");
        jdbcTemplate.update("DELETE FROM ns_user");
        jdbcTemplate.update("DELETE FROM cover_image");
    }
}
