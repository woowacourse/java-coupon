package coupon.domain.coupon;

import coupon.infra.db.CouponEntity;
import java.time.LocalDate;
import lombok.Getter;

public class Coupon {

    @Getter
    private final Long id;

    private final Name name;

    private final DiscountPolicy discountPolicy;

    @Getter
    private final CouponCategory couponCategory;

    private final CouponPeriod couponPeriod;

    public static Coupon from(CouponEntity couponEntity) {
        return new Coupon(
                couponEntity.getId(),
                new Name(couponEntity.getName()),
                new DefaultDiscountPolicy(couponEntity.getDiscountMoney(), couponEntity.getMinOrderMoney()),
                CouponCategory.valueOf(couponEntity.getCategory()),
                new CouponPeriod(couponEntity.getStartDate(), couponEntity.getEndDate())
        );
    }

    public Coupon(Name name, DiscountPolicy discountPolicy, CouponCategory couponCategory, CouponPeriod couponPeriod) {
        this(null, name, discountPolicy, couponCategory, couponPeriod);
    }

    private Coupon(
            Long id,
            Name name,
            DiscountPolicy discountPolicy,
            CouponCategory couponCategory,
            CouponPeriod couponPeriod
    ) {
        this.id = id;
        this.name = name;
        this.discountPolicy = discountPolicy;
        this.couponCategory = couponCategory;
        this.couponPeriod = couponPeriod;
    }

    public String getRawName() {
        return name.getValue();
    }

    public int getDiscountMoney() {
        return discountPolicy.getDiscountMoney();
    }

    public int getMinOrderMoney() {
        return discountPolicy.getMinOrderMoney();
    }

    public String getRawCategory() {
        return couponCategory.name();
    }

    public LocalDate getStartDate() {
        return couponPeriod.startDate();
    }

    public LocalDate getEndDate() {
        return couponPeriod.endDate();
    }
}
