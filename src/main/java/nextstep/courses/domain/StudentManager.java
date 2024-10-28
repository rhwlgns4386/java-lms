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

    public void addStudents(NsUser... students) {
        this.students.addAll(List.of(students));
    }

    public List<NsUser> getStudents() {
        return students;
    }

    public int getStudentCount() {
        return this.students.size();
    }

    public NsUser findById(Long id) {
        return this.students.stream().filter(student -> student.getId().equals(id)).findFirst().orElse(null);
    }
}
