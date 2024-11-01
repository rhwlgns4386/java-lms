package nextstep.sessions.domain;

public class SessionPeriod {
    private String startDate;
    private String endDate;

    public SessionPeriod(String startDate, String endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("강의 시작/종료일이 유효하지 않습니다");
        }
        if (startDate.isEmpty() || endDate.isEmpty()) {
            throw new IllegalArgumentException("강의 시작/종료일이 유효하지 않습니다");
        }
        if (startDate.compareTo(endDate) >= 0) {
            throw new IllegalArgumentException("시작일은 종료일보다 커거나 같을 수 없다");
        }
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStartDate() {
        return startDate;
    }
}
