package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.MemberCoupon;
import coupon.domain.repository.CouponRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional(readOnly = true)
@Service
public class CouponLookupService {

    private final CouponCache couponCache;
    private final CouponRepository couponRepository;

    public Coupon findById(Long couponId) {
        return getCachedCoupon(couponId)
                .orElseGet(() -> getCoupon(couponId));
    }

    public List<Coupon> findByMemberCoupons(List<MemberCoupon> memberCoupons) {
        List<Long> couponIds = memberCoupons.stream()
                .map(MemberCoupon::getCouponId)
                .toList();

        return findByIds(couponIds);
    }

    public List<Coupon> findByIds(List<Long> couponIds) {
        List<Coupon> coupons = new ArrayList<>();
        List<Long> missingCouponIds = new ArrayList<>();
        couponIds.forEach(couponId -> getCachedCoupon(couponId)
                .ifPresentOrElse(coupons::add, () -> missingCouponIds.add(couponId))
        );
        coupons.addAll(getCoupons(missingCouponIds));

        return coupons;
    }

    private Optional<Coupon> getCachedCoupon(Long couponId) {
        return Optional.ofNullable(couponCache.get(couponId));
    }

    private List<Coupon> getCoupons(List<Long> couponIds) {
        return couponRepository.findByIdIn(couponIds);
    }

    private Coupon getCoupon(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰이 존재하지 않습니다."));
        couponCache.cache(coupon);

        return coupon;
    }
}
