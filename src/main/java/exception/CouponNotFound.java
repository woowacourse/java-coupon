package exception;

public class CouponNotFound extends RuntimeException {

    public CouponNotFound() {
        super();
    }

    public CouponNotFound(Long id) {
        super(String.format("아이디가 %d인 쿠폰이 존재하지 않습니다.", id));
    }
}
