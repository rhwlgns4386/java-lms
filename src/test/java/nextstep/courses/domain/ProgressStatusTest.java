package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ProgressStatusTest {

    @DisplayName("강의 진행상태가 종료가 아닌지 확인한다")
    @CsvSource({"PREPARING,true", "IN_PROGRESS,true", "FINISHED,false"})
    @ParameterizedTest
    void isNotFinished(String status, boolean expected){
        ProgressStatus progressStatus = ProgressStatus.valueOf(status);
        assertThat(progressStatus.isNotFinished()).isEqualTo(expected);
    }
}
