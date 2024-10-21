package coupon.exception;

public class CouponNotFoundException extends CouponException {

    public CouponNotFoundException(Long id) {
        super("존재하지 않는 쿠폰입니다. 입력된 쿠폰 id: " + id);
    }
}
