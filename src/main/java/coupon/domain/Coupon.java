package coupon.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Coupon implements Serializable {

    private final CouponName name;
    private final DiscountAmount discountAmount;
    private final MinimumOrderAmount minimumOrderAmount;
    private final DiscountRate discountRate;
    private final ValidityPeriod validityPeriod;

    @JsonCreator
    public Coupon(
            final String name,
            final long discountAmount,
            final long minimumOrderAmount,
            final LocalDateTime startDate,
            final LocalDateTime endDate
    ) {
        this.name = new CouponName(name);
        this.discountAmount = new DiscountAmount(discountAmount);
        this.minimumOrderAmount = new MinimumOrderAmount(minimumOrderAmount);
        this.discountRate = new DiscountRate(this.discountAmount, this.minimumOrderAmount);
        this.validityPeriod = new ValidityPeriod(startDate, endDate);
    }
}
