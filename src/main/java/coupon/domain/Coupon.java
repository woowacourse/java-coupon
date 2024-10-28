package coupon.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Coupon {

    private final CouponName name;
    private final DiscountAmount discountAmount;
    private final MinimumOrderAmount minimumOrderAmount;
    private final DiscountRate discountRate;
    private final ValidityPeriod validityPeriod;

    public Coupon(
            final String name,
            final long discountAmount,
            final long minimumOrderAmount,
            final LocalDateTime startDate,
            final LocalDateTime expirationDate
    ) {
        this.name = new CouponName(name);
        this.discountAmount = new DiscountAmount(discountAmount);
        this.minimumOrderAmount = new MinimumOrderAmount(minimumOrderAmount);
        this.discountRate = new DiscountRate(this.discountAmount, this.minimumOrderAmount);
        this.validityPeriod = new ValidityPeriod(startDate, expirationDate);
    }
}
