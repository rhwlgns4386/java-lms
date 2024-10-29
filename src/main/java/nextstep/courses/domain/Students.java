package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Students {
    private static final int DEFAULT_CURRENT_COUNT = 0;

    private List<NsUser> students;

    public Students(NsUser... students) {
        this(new ArrayList<>(Arrays.asList(students)));
    }

    public Students(List<NsUser> students) {
        this.students = students;

    }

    public void addStudent(NsUser student) {
        this.students.add(student);
    }

    public int studentCount() {
        return students.size();
    }

}

