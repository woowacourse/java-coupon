package coupon.membercoupon.application;

import java.util.List;

import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.domain.MemberCouponRepository;
import coupon.membercoupon.domain.MemberCoupons;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private static final int MAX_COUPON_COUNT = 5;

    private final MemberCouponRepository memberCouponRepository;

    public MemberCoupon createMemberCoupon(MemberCoupon memberCoupon) {
        validateIssuedCouponCount(memberCoupon.getMemberId());
        return memberCouponRepository.save(memberCoupon);
    }

    private void validateIssuedCouponCount(Long memberId) {
        if (memberCouponRepository.countByMemberId(memberId) >= MAX_COUPON_COUNT) {
            throw new IllegalArgumentException("발급 가능한 최대 쿠폰 개수를 초과하였습니다. memberId: " + memberId);
        }
    }

    @Transactional
    public MemberCoupons getMemberCouponsByMemberId(Long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberId(memberId);
        return new MemberCoupons(memberCoupons);
    }
}
