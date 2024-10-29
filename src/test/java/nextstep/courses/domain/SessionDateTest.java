package nextstep.courses.domain;

import nextstep.courses.Exception.NotAllowedDateException;
import nextstep.courses.domain.SessionImage.ImageCapacity;
import nextstep.courses.domain.SessionImage.ImageSize;
import nextstep.courses.domain.SessionImage.ImageType;
import nextstep.courses.domain.SessionImage.SessionImage;
import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SessionDateTest {

    @Test
    public void 시작일이_종료일보다늦는_실패테스트() {
        assertThatThrownBy(() -> {
            final int TEST_MINUS_DAYS = 2;
            SessionDate sessionDate = new SessionDate(LocalDateTime.now(),LocalDateTime.now().minusDays(TEST_MINUS_DAYS));
        }).isInstanceOf(NotAllowedDateException.class);
    }


}
