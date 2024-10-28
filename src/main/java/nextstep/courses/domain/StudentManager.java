package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    private List<NsUser> students;

    public StudentManager() {
        this(new ArrayList<>());
    }

    public StudentManager(List<NsUser> students) {
        this.students = students;
    }

    public void addStudent(NsUser student) {
        this.students.add(student);
    }

    public void addStudents(NsUser... students) {
        this.students.addAll(List.of(students));
    }

    public int getStudentCount() {
        return this.students.size();
    }
}
