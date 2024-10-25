package coupon.coupon.dto;

import coupon.coupon.domain.Coupon;
import java.io.Serializable;
import java.time.LocalDateTime;

public record MemberCouponResponse(
        Long memberCouponId,
        Long memberId,
        boolean used,
        LocalDateTime issuedAt,
        LocalDateTime expireAt,
        Coupon coupon
) implements Serializable {

}
