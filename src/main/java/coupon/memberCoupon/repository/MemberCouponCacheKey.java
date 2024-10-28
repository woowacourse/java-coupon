package coupon.memberCoupon.repository;

import coupon.coupon.domain.Coupon;
import coupon.member.domain.Member;
import java.util.Objects;

public class MemberCouponCacheKey {

    private Member member;
    private Coupon coupon;

    public MemberCouponCacheKey(Member member, Coupon coupon) {
        this.member = member;
        this.coupon = coupon;
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
        return Objects.equals(member.getId(), that.member.getId()) && Objects.equals(coupon.getId(), that.coupon.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(member.getId(), coupon.getId());
    }
}
