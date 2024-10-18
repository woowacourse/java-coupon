package coupon.fixture;

import coupon.coupon.dto.CouponRequest;

import java.time.LocalDate;

public abstract class CouponFixture {

    public static CouponRequest COUPON_CREATE_REQUEST() {
        LocalDate now = LocalDate.now();

        return new CouponRequest(
                "대박 세일 쿠폰",
                1000,
                30000,
                "패션",
                now,
                now.plusDays(7)
        );
    }
}
