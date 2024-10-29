package coupon.infrastructure;

import java.util.List;

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

    @Override
    public List<MemberCoupon> findAllByMemberId(long memberId) {
        return memberCouponJpaRepository.findAllByMemberId(memberId);
    }

    @Override
    public int countByMemberIdAndCouponId(long memberId, long couponId) {
        return memberCouponJpaRepository.countByMemberIdAndCouponId(memberId, couponId);
    }
}
