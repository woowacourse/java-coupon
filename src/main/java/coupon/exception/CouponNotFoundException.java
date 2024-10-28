package coupon.exception;

public class CouponNotFoundException extends CustomException {

    public CouponNotFoundException() {
        super();
    }

    public CouponNotFoundException(String id) {
        super(String.format("아이디가 %s인 쿠폰이 존재하지 않습니다.", id));
    }
}
