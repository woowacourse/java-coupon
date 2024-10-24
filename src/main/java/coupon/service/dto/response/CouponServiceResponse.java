package coupon.service.dto.response;

import java.time.LocalDateTime;

public record CouponServiceResponse(String couponName, long amount, LocalDateTime start, LocalDateTime end) {
}
