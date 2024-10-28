package coupon.service;

import coupon.domain.EmptyMemberCouponDetails;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.domain.MemberCouponDetails;
import coupon.entity.CouponEntity;
import coupon.entity.MemberCouponEntity;
import coupon.entity.cache.CachedCouponEntity;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.repository.cache.CachedCouponRepository;
import coupon.support.TransactionSupporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final TransactionSupporter transactionSupporter;
    private final CachedCouponRepository cachedCouponRepository;

    @Transactional
    public MemberCoupon issue(Long couponId, Member member) {
        if (!couponRepository.existsById(couponId)) {
            throw new IllegalArgumentException("존재하지 않는 쿠폰입니다.");
        }

        int issuedCouponCount = memberCouponRepository.countByCouponIdAndMemberId(couponId, member.getId());
        MemberCoupon memberCoupon = MemberCoupon.issue(couponId, member, issuedCouponCount);

        MemberCouponEntity memberCouponEntity = memberCouponRepository.save(MemberCouponEntity.from(memberCoupon));

        return memberCouponEntity.toMemberCoupon();
    }

    @Transactional(readOnly = true)
    public List<MemberCouponDetails> getCoupons(Member member) {
        List<MemberCouponEntity> memberCoupons = getMemberCoupon(member.getId());

        return getCouponDetails(memberCoupons);
    }

    private List<MemberCouponEntity> getMemberCoupon(long memberId) {
        List<MemberCouponEntity> memberCoupons = memberCouponRepository.findAllByMemberId(memberId);
        if (memberCoupons.isEmpty()) {
            return transactionSupporter.executeNewTransaction(() -> memberCouponRepository.findAllByMemberId(memberId));
        }
        return memberCoupons;
    }

    private List<MemberCouponDetails> getCouponDetails(List<MemberCouponEntity> memberCouponEntities) {
        return memberCouponEntities.stream()
                .map(MemberCouponEntity::toMemberCoupon)
                .map(this::toMemberCouponDetails)
                .filter(MemberCouponDetails::isNotEmpty)
                .toList();
    }

    private MemberCouponDetails toMemberCouponDetails(MemberCoupon memberCoupon) {
        Optional<CachedCouponEntity> optionalCachedCouponEntity = cachedCouponRepository.findById(memberCoupon.getCouponId());
        if (optionalCachedCouponEntity.isEmpty()) {
            return getMemberCouponDetails(memberCoupon);
        }
        CachedCouponEntity cachedCouponEntity = optionalCachedCouponEntity.get();
        return new MemberCouponDetails(memberCoupon, cachedCouponEntity.toCoupon());
    }

    private MemberCouponDetails getMemberCouponDetails(MemberCoupon memberCoupon) {
        Optional<CouponEntity> optionalCouponEntity = couponRepository.findById(memberCoupon.getCouponId());
        if (optionalCouponEntity.isEmpty()) {
            optionalCouponEntity = getCouponRetry(memberCoupon.getCouponId());
        }

        return optionalCouponEntity
                .map(couponEntity -> new MemberCouponDetails(memberCoupon, couponEntity.toCoupon()))
                .orElseGet(EmptyMemberCouponDetails::new);
    }

    private Optional<CouponEntity> getCouponRetry(long id) {
        return transactionSupporter.executeNewTransaction(() -> couponRepository.findById(id));
    }
}
