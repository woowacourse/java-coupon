package coupon.membercoupon.infrastructure.repository;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Repository;

import coupon.coupon.domain.Coupon;
import coupon.coupon.infrastructure.repository.CouponEntity;
import coupon.coupon.infrastructure.repository.CouponJpaRepository;
import coupon.member.domain.Member;
import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.service.port.MemberCouponRepository;

@Repository
public class MemberCouponRepositoryImpl implements MemberCouponRepository {

    private final MemberCouponJpaRepository memberCouponJpaRepository;
    private final CouponJpaRepository couponJpaRepository;

    public MemberCouponRepositoryImpl(
            final MemberCouponJpaRepository memberCouponJpaRepository,
            final CouponJpaRepository couponJpaRepository
    ) {
        this.memberCouponJpaRepository = memberCouponJpaRepository;
        this.couponJpaRepository = couponJpaRepository;
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

    @Override
    public List<MemberCoupon> findAllByMember(final Member member) {
        return memberCouponJpaRepository.findAllByMemberId(member.getId())
                .stream()
                .map(memberCouponEntity -> convertMemberCoupon(memberCouponEntity, member))
                .toList();
    }

    private MemberCoupon convertMemberCoupon(final MemberCouponEntity memberCouponEntity, final Member member) {
        final CouponEntity couponEntity = couponJpaRepository.findById(memberCouponEntity.getCouponId())
                .orElseThrow(() -> new NoSuchElementException("해당 id의 쿠폰 정보가 존재하지 않습니다."));
        return new MemberCoupon(
                memberCouponEntity.getId(),
                member,
                couponEntity.toDomain(),
                memberCouponEntity.isUsed(),
                memberCouponEntity.getCreatedAt(),
                memberCouponEntity.getExpiredAt()
        );
    }
}
