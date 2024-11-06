package nextstep.courses.domain;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SessionStudentsTest {

    @DisplayName("수강 신청하여 수강 인원 목록에 추가한다")
    @Test
    void addStudent(){
        SessionStudents students = new SessionStudents();

        students.addStudent(new SessionStudent(10000L, NsUserTest.JAVAJIGI.getId()));

        assertThat(students.size()).isEqualTo(1);
    }

    @DisplayName("선발된 인원만 수강 승인되고 선발 안된 인원은 수강 취소된다")
    @Test
    void cancelStudent(){
        SessionStudent approveStudent = new SessionStudent(10000L, NsUserTest.JAVAJIGI.getId(), false);
        SessionStudent cancelStudent = new SessionStudent(10000L, NsUserTest.JAVAJIGI.getId(), true);

        List<SessionStudent> studentGroup = new ArrayList<>();
        studentGroup.add(approveStudent);
        studentGroup.add(cancelStudent);
        SessionStudents students = new SessionStudents(studentGroup);

        students.filterSelectedStudents();

        assertThat(students.size()).isEqualTo(1);
    }
}
