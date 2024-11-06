package nextstep.courses.domain;

import java.time.LocalDate;

public class SessionCreate {
    private final Long courseId;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final long fileSize;
    private final String fileType;
    private final int width;
    private final int height;
    private final long amount;
    private final int maxPersonnel;

    public SessionCreate(Long courseId, LocalDate startDate, LocalDate endDate, long fileSize, String fileType, int width, int height, long amount, int maxPersonnel) {
        this.courseId = courseId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.width = width;
        this.height = height;
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

    public long getFileSize() {
        return fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getAmount() {
        return amount;
    }

    public int getMaxPersonnel() {
        return maxPersonnel;
    }
}
