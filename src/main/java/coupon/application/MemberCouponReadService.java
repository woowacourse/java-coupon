package coupon.application;

import java.util.List;
import coupon.domain.coupon.CouponRepository;
import coupon.domain.membercoupon.MemberCoupon;
import coupon.domain.membercoupon.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberCouponReadService {

    private final MemberCouponRepository memberCouponRepository;
    private final CouponRepository couponRepository;

    @Cacheable(cacheNames = "member_coupon", key = "#memberId")
    public List<MemberCouponResponse> getMemberCoupons(Long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberId(memberId);

        return memberCoupons.stream()
                .map(memberCoupon -> couponRepository.findById(memberCoupon.getCoupon().getId())
                        .map(coupon -> MemberCouponResponse.of(memberCoupon, coupon))
                        .orElseThrow(IllegalArgumentException::new)
                )
                .toList();
    }
}
