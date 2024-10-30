package nextstep.courses.domain.enroll;

import nextstep.courses.domain.session.EnrollUserInfo;
import nextstep.courses.domain.session.EnrollUserInfos;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class EnrollUserInfosTest {
    private EnrollUserInfo enrollUserInfo;
    @BeforeEach
    void setUp() {
        enrollUserInfo = new EnrollUserInfo(1L, 1L);
    }

    @Test
    void 신청_회원_저장_테스트() {
        EnrollUserInfos enrollUserInfos = new EnrollUserInfos(30);
        enrollUserInfos.add(enrollUserInfo);

        assertThat(enrollUserInfos.getEnrollUserInfos()).isEqualTo(List.of(new EnrollUserInfo(1L,1L)));
    }

    @Test
    void 신청_회원_이미_수강한_신청_실패_테스트() {
        EnrollUserInfos enrollUserInfos =  new EnrollUserInfos(30);
        enrollUserInfos.add(enrollUserInfo);
        assertThatThrownBy(() -> enrollUserInfos.add(enrollUserInfo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("이미 수강 신청한 회원입니다.");
    }

    @Test
    void 신청_회원_리스트_사이즈_조회_테스트() {
        EnrollUserInfos enrollUserInfos =  new EnrollUserInfos(30);
        enrollUserInfos.add(enrollUserInfo);
        assertThat(enrollUserInfos.getSize()).isEqualTo(1L);
    }

    @Test
    void 신청_회원_리스트_사이즈_실패_테스트() {
        EnrollUserInfos enrollUserInfos =  new EnrollUserInfos(1);
        enrollUserInfos.add(enrollUserInfo);

        assertThatThrownBy(() -> enrollUserInfos.add(new EnrollUserInfo(1L, 2L)))
                .isInstanceOf(RuntimeException.class)
                .hasMessageMatching("수강 신청 인원이 꽉차서 신청 할 수 없습니다.");
    }

}
