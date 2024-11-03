package nextstep.courses.domain.session;

import nextstep.users.domain.NsUser;

import java.util.*;

public class Capacity {
    private final int maxStudents;
    private Set<Long> currentStudentIds;

    public Capacity(int maxStudents) {
        this(new HashSet<>(), maxStudents);
    }

    public Capacity(Set<Long> registeredUserIds, int maxStudents) {
        validateMaxStudents(maxStudents);
        this.currentStudentIds = new HashSet<>(registeredUserIds);
        this.maxStudents = maxStudents;
    }

    private void validateMaxStudents(int maxStudents) {
        if (maxStudents <= 0) {
            throw new IllegalArgumentException("최대 수강 인원은 0보다 커야 합니다.");
        }
    }

    public Capacity register(NsUser user) {
        validateRegistration(user);
        Set<Long> newRegisteredUserIds = new HashSet<>(currentStudentIds);
        newRegisteredUserIds.add(user.getId());
        return new Capacity(newRegisteredUserIds, maxStudents);
    }

    private void validateRegistration(NsUser user) {
        if (user == null) {
            throw new IllegalArgumentException("사용자 정보가 없습니다.");
        }
        if (isFull()) {
            throw new IllegalArgumentException("수강인원이 꽉 찼습니다.");
        }
        if (isAlreadyRegistered(user)) {
            throw new IllegalArgumentException("이미 수강신청한 사용자입니다.");
        }
    }

    public boolean isFull() {
        return currentStudentIds.size() >= maxStudents;
    }

    public boolean isAlreadyRegistered(NsUser user) {
        return currentStudentIds.contains(user.getId());
    }

    public int getMaxStudentsSize() {
        return maxStudents;
    }

    public List<Long> getRegisteredStudentIds() {
        return new ArrayList<>(currentStudentIds);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Capacity that = (Capacity) o;
        return maxStudents == that.maxStudents && Objects.equals(currentStudentIds, that.currentStudentIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxStudents, currentStudentIds);
    }
}
