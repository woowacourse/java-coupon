package coupon.memberCoupon.cache;

import java.util.Objects;
import lombok.Getter;

@Getter
public class MemberCouponCacheKey {

    private Long memberId;
    private Long couponId;

    public MemberCouponCacheKey(Long memberId, Long couponId) {
        this.memberId = memberId;
        this.couponId = couponId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MemberCouponCacheKey that = (MemberCouponCacheKey) o;
        return Objects.equals(memberId, that.getMemberId()) && Objects.equals(couponId, that.getCouponId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, couponId);
    }
}
