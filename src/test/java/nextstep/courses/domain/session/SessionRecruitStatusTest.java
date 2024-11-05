package nextstep.courses.domain.session;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionRecruitStatusTest {

    @Test
    void search() {
        SessionRecruitStatus recruitStatus = SessionRecruitStatus.search("RECRUITING");

        assertThat(recruitStatus).isEqualTo(SessionRecruitStatus.RECRUITING);
    }

    @Test
    void 지원_가능한지() {
        boolean isApply = SessionRecruitStatus.RECRUITING.isApply();

        assertThat(isApply).isTrue();
    }

}
