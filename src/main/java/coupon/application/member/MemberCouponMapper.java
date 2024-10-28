package coupon.application.member;

import java.util.List;
import coupon.domain.MemberCoupon;

public interface MemberCouponMapper {

    List<MemberCouponResponse> map(List<MemberCoupon> memberCoupons);
}
