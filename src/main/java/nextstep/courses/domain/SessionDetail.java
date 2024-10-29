package nextstep.courses.domain;

public class SessionDetail {
    private boolean isFree;
    private int maxStudentCount;
    private int sessionFee;
    public SessionDetail(boolean isFree, int maxStudentCount, int sessionFee) {
        this.isFree = isFree;
        this.maxStudentCount = maxStudentCount;
        this.sessionFee = sessionFee;
    }
}
