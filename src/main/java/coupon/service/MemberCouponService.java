package coupon.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.exception.CouponException;
import coupon.repository.CouponEntity;
import coupon.repository.MemberCouponEntity;
import coupon.repository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCouponService {

    private final CouponService couponService;
    private final MemberCouponRepository memberCouponRepository;

    public long issuedCoupon(final long couponId, final long memberId) {
        final CouponEntity coupon = couponService.getCoupon(couponId);

        final long issuedCouponCount = memberCouponRepository
                .countMemberCouponEntitiesByMemberIdAndCoupon(memberId, coupon);

        if (issuedCouponCount >= 5) {
            throw new CouponException("쿠폰은 최대 5장까지 발급할 수 있습니다.");
        }

        final MemberCouponEntity savedMemberCoupon = memberCouponRepository
                .save(new MemberCouponEntity(coupon, memberId));

        return savedMemberCoupon.getId();
    }
}
