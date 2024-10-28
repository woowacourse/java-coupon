package coupon.service;

import coupon.domain.MemberCoupon;
import coupon.repository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;

    public void issue(Long memberId, Long couponId) {
        long existCouponCount = memberCouponRepository.countByMemberIdAndCouponId(memberId, couponId);
        if (existCouponCount >= 5) {
            throw new IllegalStateException("쿠폰 발급은 최대 5개까지만 가능합니다.");
        }

        memberCouponRepository.save(new MemberCoupon(memberId, couponId));
    }
}
