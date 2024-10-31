package coupon.repository;

import coupon.domain.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;

@Repository
@RequiredArgsConstructor
public class CouponInMemoryRepository {

    private final CouponRepository couponRepository;

    @Transactional(readOnly = true)
    @Cacheable(key = "#id", value = "coupon", cacheManager = "redisCacheManager")
    public Coupon getCoupon(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 쿠폰이 존재하지 않습니다. id = " + id));
    }
}
