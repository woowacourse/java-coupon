package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.MemberCoupon;
import coupon.dto.MemberCouponResponse;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;
    private final CouponRepository couponRepository;

    public void issue(Long memberId, Long couponId) {
        long existCouponCount = memberCouponRepository.countByMemberIdAndCouponId(memberId, couponId);
        if (existCouponCount >= 5) {
            throw new IllegalStateException("쿠폰 발급은 최대 5개까지만 가능합니다.");
        }

        memberCouponRepository.save(new MemberCoupon(memberId, couponId));
    }

    public List<MemberCouponResponse> findByMemberId(Long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findByMemberId(memberId);
        return memberCoupons.stream()
                .map(memberCoupon -> {
                    Coupon coupon = couponRepository.findById(memberCoupon.getCouponId())
                            .orElseThrow(() -> new IllegalArgumentException(
                                    "해당 ID(" + memberCoupon.getCouponId() + ")의 쿠폰을 찾을 수 없습니다."));
                    return MemberCouponResponse.of(coupon, memberCoupon);
                })
                .toList();
    }
}
