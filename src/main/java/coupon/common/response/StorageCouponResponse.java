package coupon.common.response;

import coupon.domain.coupon.UserStorageCoupon;

import java.time.LocalDateTime;

public record StorageCouponResponse(UserStorageCoupon coupon, LocalDateTime issuedAt, LocalDateTime expiredAt) {
}
