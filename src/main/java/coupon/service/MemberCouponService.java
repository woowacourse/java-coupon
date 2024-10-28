package coupon.service;

import org.springframework.stereotype.Service;

import coupon.domain.MemberCoupon;
import coupon.repository.entity.MemberCouponEntity;
import coupon.repository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;

    public void provideCoupon(final MemberCoupon memberCoupon) {
        final long couponCount = memberCouponRepository.countByMemberIdAndCouponId(
                memberCoupon.getMemberId(),
                memberCoupon.getCouponId()
        );
        if (couponCount >= 5) {
            return;
        }
        memberCouponRepository.save(MemberCouponEntity.toEntity(memberCoupon));
    }
}
