package coupon.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class SaleRatio {
    private static final double MIN_SALE_RATIO = 3.0;
    private static final double MAX_SALE_RATIO = 20.0;

    @Column(name = "sale_ratio", nullable = false)
    private double ratio;

    public SaleRatio(int salePrice, int saleOrderPrice) {
        double ratio = 100.0 * salePrice / saleOrderPrice;
        validatePrice(ratio);
        this.ratio = ratio;
    }

    protected SaleRatio() {
    }

    void validatePrice(double ratio) {
        if (ratio < MIN_SALE_RATIO || ratio > MAX_SALE_RATIO) {
            throw new IllegalArgumentException("할인율은 " + MIN_SALE_RATIO + "이상 " + MAX_SALE_RATIO + "이하여야 합니다");
        }
    }

    public double getRatio() {
        return ratio;
    }
}
