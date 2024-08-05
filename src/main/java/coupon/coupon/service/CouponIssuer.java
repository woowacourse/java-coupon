package coupon.coupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.MemberCoupon;
import coupon.coupon.repository.MemberCouponRepository;
import coupon.coupon.service.observer.MemberCouponObserver;
import coupon.member.domain.Member;
import coupon.member.service.MemberService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class CouponIssuer {

    private final CouponService couponService;
    private final MemberService memberService;
    private final MemberCouponRepository memberCouponRepository;
    private final List<MemberCouponObserver> observers;

    public CouponIssuer(CouponService couponService, MemberService memberService,
                        MemberCouponRepository memberCouponRepository, List<MemberCouponObserver> observers) {
        this.couponService = couponService;
        this.memberService = memberService;
        this.memberCouponRepository = memberCouponRepository;
        this.observers = observers;
    }

    public MemberCoupon issueCoupon(Long couponId, Long memberId) {
        log.info("쿠폰 발급 요청: couponId={}, memberId={}", couponId, memberId);

        Member member = memberService.getMember(memberId);
        Coupon coupon = couponService.getCoupon(couponId);
        MemberCoupon memberCoupon = issueMemberCoupon(member, coupon);

        observers.forEach(observer -> observer.onIssue(memberCoupon.getId()));

        log.info("쿠폰 발급 완료: couponId={}, memberId={}, memberCouponId={}", couponId, memberId, memberCoupon.getId());

        return memberCoupon;
    }

    @Transactional
    public MemberCoupon issueMemberCoupon(Member member, Coupon coupon) {
        MemberCoupon memberCoupon = MemberCoupon.issue(member.getId(), coupon);
        memberCouponRepository.save(memberCoupon);
        return memberCoupon;
    }
}
