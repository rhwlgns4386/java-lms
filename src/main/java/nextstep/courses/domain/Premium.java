package nextstep.courses.domain;

public class Premium {

    private final int sessionAmount;
    private boolean isPremium;

    public Premium(boolean isPremium,int sessionAmount) {
        this.isPremium = isPremium;
        this.sessionAmount = sessionAmount;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void validateAmount(int requestAmount) {
        if (isPremium && sessionAmount!= requestAmount) {
            throw new IllegalArgumentException("강의 금액과 맞지 않습니다.");
        }
    }
}
