package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.exception.CouponException;
import coupon.repository.CouponRepository;
import coupon.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberCouponService {

    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;

    public MemberCouponService(MemberRepository memberRepository, CouponRepository couponRepository) {
        this.memberRepository = memberRepository;
        this.couponRepository = couponRepository;
    }

    @Transactional
    public MemberCoupon issueCoupon(Long memberId, Long couponId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("해당 쿠폰이 존재하지 않습니다."));

        validateMaxCouponCount(member, coupon);

        MemberCoupon memberCoupon = member.addCoupon(coupon);

        return memberCoupon;
    }

    private void validateMaxCouponCount(Member member, Coupon coupon) {
        Long couponId = coupon.getId();

        long couponCount = member.getMemberCoupons().stream()
                .filter(memberCoupon -> couponId.equals(memberCoupon.getCouponId()))
                .count();

        if (couponCount >= 5) {
            throw new CouponException("같은 쿠폰은 최대 5장까지 발급 가능합니다.");
        }
    }
}
