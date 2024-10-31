package nextstep.qna.domain;

import java.time.LocalDateTime;

public class QuestionMetaData {
    private LocalDateTime createdDate = LocalDateTime.now();

    private LocalDateTime updatedDate;

    public QuestionMetaData() {

    }

    public QuestionMetaData(LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }
}
