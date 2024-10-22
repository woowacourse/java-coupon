package coupon.coupon.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.Getter;

@Getter
public class Coupon {

    private final CouponName name;
    private final Category category;
    private final List<DiscountPolicy> discountPolicies;
    private final long discountPrice;
    private final long minimumOrderPrice;
    private final DatePeriod issuablePeriod;

    public Coupon(String name, Category category, long discountPrice, long minimumOrderPrice,
                  List<DiscountPolicy> discountPolicies, LocalDate issuableDate, LocalDate expirationDate) {
        this.name = new CouponName(name);
        this.category = category;
        this.discountPrice = discountPrice;
        this.minimumOrderPrice = minimumOrderPrice;
        this.discountPolicies = discountPolicies;
        this.issuablePeriod = new DatePeriod(issuableDate, expirationDate);

        Objects.requireNonNull(discountPolicies, "할인 정책 목록은 필수입니다.");
        discountPolicies.forEach(policy -> policy.validatePolicy(discountPrice, minimumOrderPrice));
    }

    public long discount(long orderPrice) {
        if (orderPrice < discountPrice) {
            return 0L;
        }
        return orderPrice - discountPrice;
    }

    public String getName() {
        return name.getName();
    }

    public LocalDate getIssuanceDate() {
        return issuablePeriod.getStartDateInclusive();
    }

    public LocalDate getExpirationDate() {
        return issuablePeriod.getEndDateExclusive();
    }

    public boolean isIssuable(LocalDateTime issuedTime) {
        return issuablePeriod.isBetweenPeriod(issuedTime);
    }
}
