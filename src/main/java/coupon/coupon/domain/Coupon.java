package coupon.coupon.domain;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Coupon {

    private final CouponName name;
    private final List<DiscountPolicy> discountPolicies;
    private final int discountAmount;
    private final int minimumOrderAmount;
    private final Category category;
    private final DatePeriod datePeriod;

    public Coupon(String name, List<DiscountPolicy> discountPolicies, int discountAmount, int minimumOrderAmount, Category category, LocalDate startDate, LocalDate endDate) {
        this.name = new CouponName(name);
        this.discountPolicies = discountPolicies;
        this.discountAmount = discountAmount;
        this.minimumOrderAmount = minimumOrderAmount;
        this.category = category;
        this.datePeriod = new DatePeriod(startDate, endDate);
        discountPolicies.forEach(discountPolicy -> discountPolicy.validatePolicy(discountAmount, minimumOrderAmount));
    }

    public String getName() {
        return name.getName();
    }

    public LocalDateTime getStartDate() {
        return datePeriod.getStartDate();
    }

    public LocalDateTime getEndDate() {
        return datePeriod.getEndDate();
    }
}
