package coupon.domain.coupon;

import coupon.domain.Category;
import java.time.LocalDate;
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
    private final IssuablePeriod issuablePeriod;

    public Coupon(String name, Category category, long discountPrice, long minimumOrderPrice,
                  List<DiscountPolicy> discountPolicies, LocalDate issuableDate, LocalDate expirationDate) {
        this.name = new CouponName(name);
        this.category = category;
        this.discountPrice = discountPrice;
        this.minimumOrderPrice = minimumOrderPrice;
        this.discountPolicies = discountPolicies;
        this.issuablePeriod = new IssuablePeriod(issuableDate, expirationDate);

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
        return issuablePeriod.getIssuanceDate();
    }

    public LocalDate getExpirationDate() {
        return issuablePeriod.getExpirationDate();
    }
}
