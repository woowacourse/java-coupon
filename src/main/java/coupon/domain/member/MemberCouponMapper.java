package coupon.domain.member;

import coupon.domain.coupon.Coupon;

public class MemberCouponMapper {
    private MemberCouponMapper() {
    }

    public static MemberCoupon fromEntity(coupon.data.MemberCoupon memberCoupon) {
        return new MemberCoupon(memberCoupon.getId(), memberCoupon.getCouponId(), memberCoupon.getMemberId(),
                memberCoupon.isUsed(), memberCoupon.getIssueDate(), memberCoupon.getExpirationDate());
    }

    public static coupon.data.MemberCoupon toEntity(MemberCoupon memberCoupon) {
        return new coupon.data.MemberCoupon(memberCoupon.getId(), memberCoupon.getCouponId(),
                memberCoupon.getMemberId(),
                memberCoupon.isUsed(), memberCoupon.getIssueDate(), memberCoupon.getExpirationDate()
        );
    }

}
