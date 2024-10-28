package coupon.membercoupon.service;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import coupon.coupons.domain.Coupon;
import coupon.member.domain.Member;
import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.domain.MemberCoupons;
import coupon.membercoupon.repository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;

    @Transactional
    public MemberCoupon issue(Member member, Coupon coupon) {
        MemberCoupons memberCoupons = new MemberCoupons(memberCouponRepository.findAllByMemberId(member.getId()));
        memberCoupons.validateCouponIssuanceLimit();
        MemberCoupon memberCoupon = new MemberCoupon(coupon, member, LocalDateTime.now());
        return memberCouponRepository.save(memberCoupon);
    }
}
