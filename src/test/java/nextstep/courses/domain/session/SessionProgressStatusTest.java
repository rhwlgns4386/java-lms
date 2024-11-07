package nextstep.courses.domain.session;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SessionProgressStatusTest {
    @Test
    void search() {
        SessionProgressStatus progressStatus = SessionProgressStatus.search("IN_PROGRESS");

        assertThat(progressStatus).isEqualTo(SessionProgressStatus.IN_PROGRESS);
    }

    @Test
    void 지원_가능한지() {
        boolean isApply = SessionProgressStatus.IN_PROGRESS.isApply();

        assertThat(isApply).isTrue();
    }
}
