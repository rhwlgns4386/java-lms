package nextstep.courses.domain;

public class SessionPrice {

    private final long salePrice;

    public SessionPrice(final long salePrice) {
        if (isLessThanTo(salePrice)) {
            throw new IllegalArgumentException("판매가가 올바르지 않습니다.");
        }
        this.salePrice = salePrice;
    }

    public long getSalePrice() {
        return salePrice;
    }

    public boolean isLessThanOrEqualTo(long salePrice) {
        if (this.salePrice < salePrice) {
            return true;
        }
        return false;
    }

    public boolean isLessThanTo(long salePrice) {
        if (salePrice < 0) {
            return false;
        }
        return true;
    }
}
