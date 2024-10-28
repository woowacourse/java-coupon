package coupon.service;

import coupon.CouponException;
import coupon.dto.CouponCreateRequest;
import coupon.entity.Coupon;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @CachePut(key = "#result.id", value = "coupon", cacheManager = "replicationLagCacheManager")
    @Transactional
    public Coupon create(CouponCreateRequest request) {
        Coupon coupon = new Coupon(
                request.name(),
                request.discount(),
                request.minimumOrder(),
                request.category(),
                request.start(),
                request.end()
        );
        return couponRepository.save(coupon);
    }

    @Cacheable(key = "#id", value = "coupon")
    @Transactional(readOnly = true)
    public Coupon read(long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new CouponException("coupon with id " + id + " not found"));
    }
}
