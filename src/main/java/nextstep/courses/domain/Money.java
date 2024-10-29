package nextstep.courses.domain;

public class Money {
    private long price;

    public Money(long price) {
        if(price <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        this.price = price;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Money)) {
            return false;
        }

        Money money = (Money) o;
        return price == money.price;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(price);
    }
}
