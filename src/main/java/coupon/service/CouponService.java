package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.domain.repository.CouponRepository;
import coupon.domain.repository.MemberCouponRepository;
import coupon.domain.repository.MemberRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createCoupon(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseGet(() -> getCouponFromWriter(couponId));
    }

    @Transactional
    public Coupon getCouponFromWriter(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public void issueCouponToMember(Long memberId, Long couponId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰이 존재하지 않습니다."));

        if (member.getCouponCount() >= 5) {
            throw new IllegalArgumentException("한 회원은 최대 5장의 쿠폰만 발급받을 수 있습니다.");
        }

        coupon.decrementAvailableCount();
        MemberCoupon memberCoupon = new MemberCoupon(coupon, member, LocalDateTime.now(), LocalDateTime.now().plusDays(7));
        memberCouponRepository.save(memberCoupon);
    }
}

