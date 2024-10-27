package coupon.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.Coupon;
import coupon.domain.DefaultMemberCouponPolicy;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final TransactionService transactionService;
    private final MemberCouponRepository memberCouponRepository;

    @Transactional
    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(long id) {
        return couponRepository.findById(id)
                .orElseGet(() -> getCouponFromWriter(id));
    }

    public Coupon getCouponFromWriter(long id) {
        return transactionService.run(() -> couponRepository.fetchCouponById(id));
    }

    @Transactional
    public MemberCoupon issueMemberCoupon(Coupon coupon, Member member) {
        var memberCouponCount = memberCouponRepository.countByMember(member);
        var memberCouponIssuePolicy = new DefaultMemberCouponPolicy(memberCouponCount);
        memberCouponIssuePolicy.validate();
        var memberCoupon = new MemberCoupon(coupon, member, LocalDateTime.now());
        return memberCouponRepository.save(memberCoupon);
    }
}
