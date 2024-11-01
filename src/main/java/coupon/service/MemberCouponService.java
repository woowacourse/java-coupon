package coupon.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.Coupon;
import coupon.domain.CouponRepository;
import coupon.domain.MemberCoupon;
import coupon.domain.MemberCouponRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private static final int MAX_COUPON_COUNT = 5;

    private final MemberCouponRepository memberCouponRepository;
    private final CouponRepository couponRepository;

    public MemberCoupon addCoupon(long memberId, long couponId) {
        MemberCoupon memberCoupon = new MemberCoupon(couponId, memberId, LocalDateTime.now());

        int issuedCount = memberCouponRepository.countByMemberIdAndCouponId(memberId, couponId);
        if (issuedCount >= MAX_COUPON_COUNT) {
            throw new IllegalStateException("동일한 쿠폰은 회원당 최대 5장까지만 발급 가능합니다.");
        }

        return memberCouponRepository.save(memberCoupon);
    }

    @Transactional(readOnly = true)
    public List<Coupon> getMemberCoupons(long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberId(memberId);

        return memberCoupons.stream()
            .map(memberCoupon -> couponRepository.findById(memberCoupon.getCouponId()))
            .toList();
    }
}
