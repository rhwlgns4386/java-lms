package nextstep.courses.domain;

public enum ProgressStatus {
    PREPARING("준비중"),
    IN_PROGRESS("진행중"),
    FINISHED("종료");

    private final String description;

    ProgressStatus(String description) {
        this.description = description;
    }

    public boolean isNotFinished(){
        return this != FINISHED;
    }
}
