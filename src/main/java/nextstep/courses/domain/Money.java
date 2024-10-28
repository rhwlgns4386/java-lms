package nextstep.courses.domain;

public class Money {
    private final int amount;

    public Money(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("금액은 0보다 작을 수 없습니다.");
        }

        this.amount = amount;
    }
}
