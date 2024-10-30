package nextstep.courses.domain.enroll;

import nextstep.courses.domain.session.EnrollUserInfo;
import nextstep.courses.domain.session.EnrollUserInfos;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class EnrollUserInfosTest {

    @Test
    void 신청_회원_저장_테스트() {

        EnrollUserInfo enrollUserInfo = new EnrollUserInfo(1L, 1L);

        EnrollUserInfos enrollUserInfos = new EnrollUserInfos();
        enrollUserInfos.add(enrollUserInfo);

        assertThat(enrollUserInfos).isEqualTo(new EnrollUserInfos(List.of(new EnrollUserInfo(1L, 1L))));
    }

    @Test
    void 신청_회원_실패_테스트() {
        EnrollUserInfo enrollUserInfo = new EnrollUserInfo(1L, 1L);
        EnrollUserInfos enrollUserInfos = new EnrollUserInfos(List.of(new EnrollUserInfo(1L, 1L)));

        assertThatThrownBy(() -> enrollUserInfos.add(enrollUserInfo))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("이미 수강 신청한 회원입니다.");
    }

    @Test
    void 신청_회원_리스트_사이즈_조회_테스트() {
        EnrollUserInfos enrollUserInfos = new EnrollUserInfos(List.of(new EnrollUserInfo(1L, 1L)));

        assertThat(enrollUserInfos.getSize()).isEqualTo(1L);
    }

    @Test
    void 신청_회원_리스트_조회_테스트() {
        EnrollUserInfos enrollUserInfos = new EnrollUserInfos(List.of(new EnrollUserInfo(1L, 1L)));

        assertThat(enrollUserInfos.getEnrollUserInfos()).isEqualTo(List.of(new EnrollUserInfo(1L,1L)));
    }

}
