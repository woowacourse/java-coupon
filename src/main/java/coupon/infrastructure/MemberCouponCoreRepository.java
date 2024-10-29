package coupon.infrastructure;

import org.springframework.stereotype.Repository;

import coupon.domain.MemberCoupon;
import coupon.domain.MemberCouponRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberCouponCoreRepository implements MemberCouponRepository {

    private final MemberCouponJpaRepository memberCouponJpaRepository;

    @Override
    public MemberCoupon save(MemberCoupon memberCoupon) {
        return memberCouponJpaRepository.save(memberCoupon);
    }
}
