package coupon.coupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.MemberCoupon;
import coupon.coupon.domain.repository.CouponRepository;
import coupon.coupon.domain.repository.MemberCouponRepository;
import coupon.coupon.exception.CouponNotFoundException;
import coupon.member.domain.Member;
import coupon.member.domain.repository.MemberRepository;
import coupon.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberCouponService {

    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void issueCoupon(long memberId, long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException(couponId));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));

        coupon.issue();
        memberCouponRepository.save(new MemberCoupon(member, coupon));
    }
}
