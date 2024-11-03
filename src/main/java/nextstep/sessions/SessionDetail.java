package nextstep.sessions;

import nextstep.users.domain.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SessionDetail {
    public static final String SESSION_FULL_MESSAGE = "해당 강의는 수강인원이 모두 찼습니다.";
    private final boolean isFree;
    private final int maxStudentCount;
    private final Long sessionFee;
    private static final int MIN_STUDENT_COUNT = 1;
    private static final int MAX_STUDENT_COUNT = 999;
    private List<Student> students = new ArrayList<>();

    public SessionDetail(boolean isFree, int maxStudentCount, Long sessionFee) {
        validateStudentCount(maxStudentCount);
        this.isFree = isFree;
        this.maxStudentCount = maxStudentCount;
        this.sessionFee = sessionFee != null ? sessionFee : 0L;
    }

    public SessionDetail(boolean isFree, int maxStudentCount) {
        this(isFree, maxStudentCount, 0L);
    }

    private void validateStudentCount(int maxStudentCount) {
        if (maxStudentCount < MIN_STUDENT_COUNT || maxStudentCount > MAX_STUDENT_COUNT) {
            throw new IllegalArgumentException("학생 수는 최소 " + MIN_STUDENT_COUNT + "명 이상, " + MAX_STUDENT_COUNT + "명 이하여야 합니다.");
        }
    }

    public boolean isSameAmount(Long amount) {
        return amount.equals(this.sessionFee);
    }

    public boolean isAllowable() {
        return students.size() < maxStudentCount;
    }

    public void registerNewStudent(Student student) {
        students.add(student);
    }

    public void checkRegistrationEligibility(Long cost) {
        if (!isSameAmount(cost)) {
            throw new IllegalArgumentException("수납해야할 강의료가 일치하지 않습니다. 강의료 : " + sessionFee + "원");
        }

        if (!isAllowable()) {
            throw new IllegalStateException(SESSION_FULL_MESSAGE);
        }
    }

    public boolean contains(Student student) {
        return students.contains(student);
    }
}
