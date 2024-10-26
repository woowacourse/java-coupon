package coupon.coupon.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import coupon.coupon.CouponException;

@Entity
public class Coupon {

    private static final BigDecimal MINIMUM_DISCOUNT_RATE = BigDecimal.valueOf(3);
    private static final BigDecimal MAXIMUM_DISCOUNT_RATE = BigDecimal.valueOf(20);
    private static final String DISCOUNT_RATE_MESSAGE = String.format(
            "할인율은 %s%% 이상, %s%% 이하이어야 합니다.",
            MINIMUM_DISCOUNT_RATE.toPlainString(),
            MAXIMUM_DISCOUNT_RATE.toPlainString()
    );

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private CouponName name;
    @Embedded
    private DiscountAmount discountAmount;
    @Embedded
    private MinimumOrderAmount minimumOrderAmount;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Embedded
    private Term term;

    protected Coupon() {

    }

    public Coupon(String name, long discountAmount, long minimumOrderAmount, Category category, LocalDate startAt, LocalDate endAt) {
        this(new CouponName(name), new DiscountAmount(discountAmount), new MinimumOrderAmount(minimumOrderAmount), category, new Term(startAt, endAt));
    }

    public Coupon(CouponName name, DiscountAmount discountAmount, MinimumOrderAmount minimumOrderAmount, Category category, Term term) {
        validateDiscountRate(discountAmount, minimumOrderAmount);
        this.name = name;
        this.discountAmount = discountAmount;
        this.minimumOrderAmount = minimumOrderAmount;
        this.category = category;
        this.term = term;
    }

    private void validateDiscountRate(DiscountAmount discountAmount, MinimumOrderAmount minimumOrderAmount) {
        BigDecimal discountRate = discountAmount.getDiscountAmount()
                .multiply(BigDecimal.valueOf(100))
                .divide(minimumOrderAmount.getMinimumOrderAmount(), 0, RoundingMode.DOWN);
        if (discountRate.compareTo(MINIMUM_DISCOUNT_RATE) < 0 || discountRate.compareTo(MAXIMUM_DISCOUNT_RATE) > 0) {
            throw new CouponException(DISCOUNT_RATE_MESSAGE);
        }
    }

    public long getId() {
        return id;
    }
}
