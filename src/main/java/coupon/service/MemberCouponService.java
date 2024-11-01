package coupon.service;

import java.util.List;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.Coupon;
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

    @CachePut(value = "coupons", key = "#memberId", condition = "#memberId!=null")
    public long issuedCoupon(final long couponId, final long memberId) {
        final CouponEntity coupon = couponService.getCoupon(couponId);

        final long issuedCouponCount = memberCouponRepository
                .countMemberCouponEntitiesByMemberIdAndCoupon(memberId, coupon);

        if (issuedCouponCount >= 5) {
            throw new CouponException("쿠폰은 최대 5장까지 발급할 수 있습니다.");
        }

        final MemberCouponEntity saved = memberCouponRepository.save(new MemberCouponEntity(coupon, memberId));
        return saved.getId();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "coupons", key = "#memberId", condition = "#memberId!=null")
    public List<Coupon> findAllCoupon(@Param("memberId") final long memberId) {
        final List<MemberCouponEntity> coupons = memberCouponRepository.findAllByMemberId(memberId);

        return coupons.stream()
                .map(MemberCouponEntity::getCoupon)
                .map(CouponEntity::toDomain)
                .toList();
    }
}
