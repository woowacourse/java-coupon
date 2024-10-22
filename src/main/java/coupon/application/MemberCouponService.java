package coupon.application;

import coupon.domain.MemberCoupon;
import coupon.domain.MemberCouponRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberCouponService {

    private static final int MIN_MEMBER_COUPON_COUNT = 5;

    private final MemberCouponRepository memberCouponRepository;

    @Transactional
    public void create(Long memberId, Long couponId, LocalDate issueDate) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findByCouponIdAndMemberId(couponId, memberId);

        validateMemberCouponsCount(memberCoupons);

        MemberCoupon memberCoupon = new MemberCoupon(couponId, memberId, issueDate);
        memberCouponRepository.save(memberCoupon);
    }

    private void validateMemberCouponsCount(List<MemberCoupon> memberCoupons) {
        if (memberCoupons.size() >= MIN_MEMBER_COUPON_COUNT) {
            throw new IllegalArgumentException("Too many coupons");
        }
    }
}
