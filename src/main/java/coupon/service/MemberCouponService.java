package coupon.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.Coupon;
import coupon.domain.DefaultMemberCouponPolicy;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.repository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;
    private final CouponService couponService;

    @Transactional
    public MemberCoupon issueMemberCoupon(Member member, Coupon coupon) {
        var memberCouponCount = memberCouponRepository.countByMemberId(member.getId());
        var memberCouponIssuePolicy = new DefaultMemberCouponPolicy(memberCouponCount);
        memberCouponIssuePolicy.validate();
        var memberCoupon = new MemberCoupon(member, coupon);
        return memberCouponRepository.save(memberCoupon);
    }

    public List<Coupon> findAllCouponByMember(Member member) {
        var memberCoupons = memberCouponRepository.findAllByMemberId(member.getId());
        return memberCoupons.stream()
                .map(MemberCoupon::getCouponId)
                .map(couponService::getCoupon)
                .toList();
    }
}
