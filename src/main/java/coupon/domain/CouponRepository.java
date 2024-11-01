package coupon.domain;

public interface CouponRepository {

    Coupon save(Coupon coupon);

    Coupon findById(long id);
}
