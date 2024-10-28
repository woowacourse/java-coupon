package coupon.service;

import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.entity.MemberCouponEntity;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;

    @Transactional
    public MemberCoupon issue(Long couponId, Member member) {
        if (!couponRepository.existsById(couponId)) {
            throw new IllegalArgumentException("존재하지 않는 쿠폰입니다.");
        }
        MemberCoupon memberCoupon = MemberCoupon.issue(1L, member);

        MemberCouponEntity memberCouponEntity = memberCouponRepository.save(MemberCouponEntity.from(memberCoupon));

        return memberCouponEntity.toMemberCoupon();
    }

}
