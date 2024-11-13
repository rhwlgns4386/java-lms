package nextstep.courses.domain;

import java.time.LocalDate;

public class SessionCreate {
    private final Long courseId;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final long amount;
    private final int maxPersonnel;

    public SessionCreate(Long courseId, LocalDate startDate, LocalDate endDate, long amount, int maxPersonnel) {
        this.courseId = courseId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.maxPersonnel = maxPersonnel;
    }

    public Long getCourseId() {
        return courseId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public long getAmount() {
        return amount;
    }

    public int getMaxPersonnel() {
        return maxPersonnel;
    }
}
