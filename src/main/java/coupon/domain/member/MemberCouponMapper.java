package coupon.domain.member;

import coupon.data.MemberCouponEntity;

public class MemberCouponMapper {
    private MemberCouponMapper() {
    }

    public static MemberCoupon fromEntity(MemberCouponEntity memberCouponEntity) {
        return new MemberCoupon(memberCouponEntity.getId(), memberCouponEntity.getCouponId(), memberCouponEntity.getMemberId(),
                memberCouponEntity.isUsed(), memberCouponEntity.getIssueDate(), memberCouponEntity.getExpirationDate());
    }

    public static MemberCouponEntity toEntity(MemberCoupon memberCoupon) {
        return new MemberCouponEntity(memberCoupon.getId(), memberCoupon.getCouponId(),
                memberCoupon.getMemberId(),
                memberCoupon.isUsed(), memberCoupon.getIssueDate(), memberCoupon.getExpirationDate()
        );
    }

}
