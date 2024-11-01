package coupon.domain.coupon;

import coupon.data.CouponEntity;

public class CouponMapper {

    private CouponMapper() {
    }

    public static Coupon fromEntity(CouponEntity couponEntity) {
        Accounting accounting = new Accounting(couponEntity.getDiscountAmount(), couponEntity.getMinimumOrderAmount());
        Category category = Category.from(couponEntity.getCategory());
        Duration duration = new Duration(couponEntity.getBeginAt().toLocalDate(), couponEntity.getEndAt().toLocalDate());
        Name name = new Name(couponEntity.getName());

        return new Coupon(name, accounting, category, duration);
    }

    public static CouponEntity toEntity(Coupon coupon) {
        return new CouponEntity(
                coupon.getName().getValue(),
                coupon.getAccounting().getDiscountAmount(),
                coupon.getAccounting().getMinimumOrderCost(),
                coupon.getCategory().toString(),
                coupon.getDuration().getBeginAt(),
                coupon.getDuration().getEndAt()
        );
    }
}
