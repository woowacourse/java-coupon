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

    @Transactional
    public MemberCoupon issueMemberCoupon(Coupon coupon, Member member) {
        var memberCouponCount = memberCouponRepository.countByMember(member);
        var memberCouponIssuePolicy = new DefaultMemberCouponPolicy(memberCouponCount);
        memberCouponIssuePolicy.validate();
        var memberCoupon = new MemberCoupon(member, coupon);
        return memberCouponRepository.save(memberCoupon);
    }

    @Transactional(readOnly = true)
    public List<Coupon> findAllCouponByMember(Member member) {
        var memberCoupons = memberCouponRepository.findAllByMember(member);
        return memberCoupons.stream()
                .map(MemberCoupon::getCoupon)
                .toList();
    }
}
