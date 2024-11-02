package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.List;

public class Students {
    private List<NsUser> students = new ArrayList<>();

    public Students(List<NsUser> students) {
        this.students.addAll(students);
    }

    public List<NsUser> getStudents() {
        return students;
    }

    public int getSize() {
        return students.size();
    }

    public void updateStudent(NsUser student) {
        students.add(student);
    }

    public boolean isDuplicateStudent(NsUser student) {
        if (students.contains(student)) {
            return true;
        }
        return false;
    }
}
