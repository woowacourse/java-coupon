package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberCouponService {

    private static final int MAX_COUPON_COUNT = 5;

    private final MemberCouponRepository memberCouponRepository;
    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public MemberCoupon issueCoupon(long memberId, long couponId) {
        Member member = memberRepository.getById(memberId);
        Coupon coupon = couponRepository.getById(couponId);
        validateCouponCountPerMember(member, coupon);
        return memberCouponRepository.save(new MemberCoupon(coupon, member));
    }

    private void validateCouponCountPerMember(Member member, Coupon coupon) {
        int couponCountPerMember = memberCouponRepository.countByMemberAndCoupon(member, coupon);
        if (couponCountPerMember >= MAX_COUPON_COUNT) {
            throw new IllegalArgumentException("멤버 한 명당 동일한 쿠폰은 5장까지 발급 가능합니다.");
        }
    }
}
