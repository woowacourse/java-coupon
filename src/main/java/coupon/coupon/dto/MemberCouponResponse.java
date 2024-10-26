package coupon.coupon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.MemberCoupon;
import java.time.LocalDateTime;

public record MemberCouponResponse(

        long couponId,
        String name,
        int discountAmount,
        int orderPrice,
        Enum category,
        boolean used,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime issuedAt,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime expiredAt
) {

    public static MemberCouponResponse of(MemberCoupon memberCoupon, Coupon coupon) {
        return new MemberCouponResponse(
                memberCoupon.getCouponId(),
                coupon.getName().getValue(),
                coupon.getDiscountAmount().getValue(),
                coupon.getOrderPrice().getValue(),
                coupon.getCategory(),
                memberCoupon.isUsed(),
                memberCoupon.getIssueAt(),
                memberCoupon.getExpiredAt()
        );
    }
}
