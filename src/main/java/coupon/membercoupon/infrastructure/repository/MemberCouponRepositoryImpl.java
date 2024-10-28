package coupon.membercoupon.infrastructure.repository;

import org.springframework.stereotype.Repository;

import coupon.coupon.domain.Coupon;
import coupon.member.domain.Member;
import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.service.port.MemberCouponRepository;

@Repository
public class MemberCouponRepositoryImpl implements MemberCouponRepository {

    private final MemberCouponJpaRepository memberCouponJpaRepository;

    public MemberCouponRepositoryImpl(final MemberCouponJpaRepository memberCouponJpaRepository) {
        this.memberCouponJpaRepository = memberCouponJpaRepository;
    }

    @Override
    public int countByMemberAndCoupon(final Member member, final Coupon coupon) {
        return memberCouponJpaRepository.countAllByMemberIdAndCouponId(member.getId(), coupon.getId());
    }

    @Override
    public void save(final MemberCoupon memberCoupon) {
        final MemberCouponEntity memberCouponEntity = new MemberCouponEntity(memberCoupon);
        memberCouponJpaRepository.save(memberCouponEntity);
    }
}
