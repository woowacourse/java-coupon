package coupon.membercoupon.application;

import java.util.List;
import java.util.Map;
import coupon.coupon.application.CouponService;
import coupon.coupon.domain.Coupon;
import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.domain.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private final CouponService couponService;
    private final MemberCouponRepository memberCouponRepository;
    private final MemberCouponMapper memberCouponMapper;

    @Transactional(readOnly = true)
    public List<MemberCouponWithCouponResponse> getMemberCouponWithCoupons(Long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberId(memberId);
        List<Long> couponIds = memberCoupons.stream()
                .map(MemberCoupon::getCouponId)
                .toList();
        Map<Long, Coupon> couponMap = couponService.getCouponMapEachById(couponIds);

        return memberCouponMapper.toWithCouponResponses(memberCoupons, couponMap);
    }
}
