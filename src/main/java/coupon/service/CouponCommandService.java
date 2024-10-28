package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CouponCommandService {

    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;
    private final MemberCouponRepository memberCouponRepository;

    public CouponCommandService(CouponRepository couponRepository, MemberRepository memberRepository,
                                MemberCouponRepository memberCouponRepository) {
        this.couponRepository = couponRepository;
        this.memberRepository = memberRepository;
        this.memberCouponRepository = memberCouponRepository;
    }

    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    public Coupon getCoupon(Long id) {
        return couponRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));
    }

    public void issueCoupon(Long memberId, Long couponId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 멤버입니다."));
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));

        MemberCoupon memberCoupon = new MemberCoupon(member, coupon);
        memberCouponRepository.save(memberCoupon);
    }
}
