package nextstep.courses.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class SessionSelectionTest {

    @Test
    @DisplayName("선발 여부를 체크한다.")
    void 선발(){
        Assertions.assertAll(
                ()->assertThat(SessionSelection.ALL_SELECT.select()).isTrue(),
                ()->assertThat(SessionSelection.ALL_UNSELECT.select()).isFalse()
        );
    }
}
