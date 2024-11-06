package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;

public class SessionStudents {
    private List<SessionStudent> students;

    public SessionStudents() {
        students = new ArrayList<>();
    }

    public SessionStudents(List<SessionStudent> students) {
        this.students = students;
    }

    public void addStudent(SessionStudent student) {
        checkAddStudent(student);
        students.add(student);
    }

    public void filterSelectedStudents() {
        List<SessionStudent> approveStudents = new ArrayList<>();
        for (SessionStudent student : students) {
            if(student.isNotSelected()) continue;
            student.updateSelected();
            approveStudents.add(student);
        }
        students = approveStudents;
    }

    public int size() {
        return students.size();
    }

    private void checkAddStudent(SessionStudent student) {
        checkExistingStudent(student);
    }

    private void checkExistingStudent(SessionStudent student) {
        boolean isExistingStudent = students.stream()
                .map(SessionStudent::getNsUserId)
                .anyMatch(nsUserId -> student.getNsUserId().equals(nsUserId));

        if (isExistingStudent) {
            throw new IllegalStateException("이미 수강신청한 유저입니다.");
        }
    }

    public List<SessionStudent> getStudents() {
        return new ArrayList<>(students);
    }
}
