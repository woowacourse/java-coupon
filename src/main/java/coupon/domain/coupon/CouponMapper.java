package coupon.domain.coupon;

public class CouponMapper {

    private CouponMapper() {
    }

    public static Coupon fromEntity(coupon.data.Coupon coupon) {
        Accounting accounting = new Accounting(coupon.getDiscountAmount(), coupon.getMinimumOrderAmount());
        Category category = Category.from(coupon.getCategory());
        Duration duration = new Duration(coupon.getBeginAt().toLocalDate(), coupon.getEndAt().toLocalDate());
        Name name = new Name(coupon.getName());

        return new Coupon(name, accounting, category, duration);
    }

    public static coupon.data.Coupon toEntity(Coupon coupon) {
        return new coupon.data.Coupon(
                coupon.getName().getValue(),
                coupon.getAccounting().getDiscountAmount(),
                coupon.getAccounting().getMinimumOrderCost(),
                coupon.getCategory().toString(),
                coupon.getDuration().getBeginAt(),
                coupon.getDuration().getEndAt()
        );
    }
}
