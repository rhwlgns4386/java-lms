package nextstep.courses.domain;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

class SessionStudentTest {

    @DisplayName("동일한 유저는 중복으로 수강신청할 수 없다.")
    @Test
    void checkExistingStudent() {
        SessionStudents student = new SessionStudents();
        Pricing pricing = new Pricing(PricingType.PAID, 10000);

        student.addStudent(new SessionStudent(10000L, NsUserTest.JAVAJIGI.getId()));

        assertThatIllegalStateException()
                .isThrownBy(() -> student.addStudent(new SessionStudent(10000L, NsUserTest.JAVAJIGI.getId())))
                .withMessage("이미 수강신청한 유저입니다.");
    }

    @DisplayName("수강신청 성공하면 유저를 등록한다")
    @Test
    void addStudent() {
        SessionStudents student = new SessionStudents();
        
        student.addStudent(new SessionStudent(10000L, NsUserTest.JAVAJIGI.getId()));
        student.addStudent(new SessionStudent(10000L, NsUserTest.SANJIGI.getId()));

        assertThat(student.size()).isEqualTo(2);
    }

}
