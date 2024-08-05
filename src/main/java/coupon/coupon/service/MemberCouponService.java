package coupon.coupon.service;

import static java.util.stream.Collectors.toList;

import coupon.coupon.domain.MemberCoupon;
import coupon.coupon.repository.MemberCouponRepository;
import coupon.coupon.service.observer.MemberCouponObserver;
import coupon.member.domain.Member;
import coupon.member.service.MemberService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;
    private final MemberService memberService;
    private final List<MemberCouponObserver> observers;

    public MemberCouponService(MemberCouponRepository memberCouponRepository,
                               MemberService memberService, List<MemberCouponObserver> observers) {
        this.memberCouponRepository = memberCouponRepository;
        this.memberService = memberService;
        this.observers = observers;
    }

    @Transactional(readOnly = true)
    public List<MemberCoupon> findUsableMemberCoupons(Long memberId) {
        return memberCouponRepository.findByMemberIdAndUsedAndUseEndedAtAfter(memberId, false, LocalDateTime.now())
                .stream()
                .filter(memberCoupon -> memberCoupon.getCoupon().isUsableCoupon())
                .collect(toList());
    }

    @Transactional
    public void useCoupon(Long memberId, Long memberCouponId) {
        Member member = memberService.getMember(memberId);
        MemberCoupon memberCoupon = memberCouponRepository.findById(memberCouponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 쿠폰입니다. memberCouponId: " + memberCouponId));
        if (!Objects.equals(memberCoupon.getMemberId(), member.getId())) {
            throw new IllegalArgumentException(
                    "해당 회원의 쿠폰이 아닙니다. memberId: " + memberId + ", memberCouponId: " + memberCouponId);
        }

        memberCoupon.use();
        observers.forEach(observer -> observer.onUse(memberCoupon.getId()));
    }

    @Transactional(readOnly = true)
    public Long findIssuedCouponCount(Long couponId) {
        return memberCouponRepository.countByCoupon_Id(couponId);
    }

    @Transactional(readOnly = true)
    public Long findUsedCouponCount(Long couponId) {
        return memberCouponRepository.countByCoupon_IdAndUsed(couponId, true);
    }
}
