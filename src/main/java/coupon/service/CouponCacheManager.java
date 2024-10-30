package coupon.service;

import coupon.cache.CachedCoupon;
import coupon.domain.Coupon;
import coupon.repository.CachedCouponRepository;
import coupon.repository.CouponRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponCacheManager {
    private final CouponRepository couponRepository;
    private final CachedCouponRepository cachedCouponRepository;

    public void update(Coupon coupon) {
        cachedCouponRepository.findById(coupon.getId())
                .ifPresent(cachedCoupon -> cachedCouponRepository.save(new CachedCoupon(coupon)));
    }

    public List<Coupon> finAllByIds(Set<Long> couponIds) {
        List<Coupon> cachedCoupons = findCachedCoupons(couponIds);

        Set<Long> cachedCouponIds = cachedCoupons.stream()
                .map(Coupon::getId)
                .collect(Collectors.toSet());
        Set<Long> couponIdsNotInCache = couponIds.stream()
                .filter(id -> !cachedCouponIds.contains(id))
                .collect(Collectors.toSet());

        List<Coupon> coupons = new ArrayList<>(cachedCoupons);
        if (couponIdsNotInCache.size() > 0) {
            List<Coupon> couponsNotInCache = couponRepository.findAllByIdIn(couponIdsNotInCache);
            coupons.addAll(couponsNotInCache);
            saveToCache(couponsNotInCache);
        }
        return coupons;
    }

    private List<Coupon> findCachedCoupons(Set<Long> couponIds) {
        return couponIds.stream()
                .map(cachedCouponRepository::findById)
                .flatMap(Optional::stream)
                .map(CachedCoupon::getCoupon)
                .toList();
    }

    private void saveToCache(List<Coupon> couponsNotInCache) {
        List<CachedCoupon> cachedCoupons = couponsNotInCache.stream()
                .map(CachedCoupon::new)
                .toList();
        cachedCouponRepository.saveAll(cachedCoupons);
    }
}
