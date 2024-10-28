package coupon.dto;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.MemberCoupon;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record MemberCouponResponse(Long couponId, String name, int minimumOrderAmount,
                                   int discountAmount, int discountRate, Category category,
                                   LocalDateTime issueStartDate, LocalDateTime issueEndDate, Long memberCouponId,
                                   boolean isUsed, LocalDate issuedAt, LocalDate expiredAt) {

    public static MemberCouponResponse of(Coupon coupon, MemberCoupon memberCoupon) {
        return new MemberCouponResponse(
                coupon.getId(), coupon.getName(), coupon.getMinimumOrderAmount(),
                coupon.getDiscountAmount(), coupon.getDiscountRate(), coupon.getCategory(),
                coupon.getIssueStartDate(), coupon.getIssueEndDate(), memberCoupon.getId(),
                memberCoupon.isUsed(), memberCoupon.getIssueAt(), memberCoupon.getExpiredAt()
        );
    }
}

