package coupon.service;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.dto.CouponRequest;
import coupon.repository.CategoryRepository;
import coupon.repository.CouponRepository;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponService {
    private static final String COUPON_CACHE_NAME = "coupon";

    private final CouponRepository couponRepository;
    private final CategoryRepository categoryRepository;

    public CouponService(CouponRepository couponRepository, CategoryRepository categoryRepository) {
        this.couponRepository = couponRepository;
        this.categoryRepository = categoryRepository;
    }

    @CachePut(cacheNames = COUPON_CACHE_NAME, key = "#result.id")
    @Transactional
    public Coupon create(CouponRequest couponRequest) {
        Category category = categoryRepository.findById(couponRequest.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        return couponRepository.save(couponRequest.toEntity(category));
    }

    @Cacheable(cacheNames = COUPON_CACHE_NAME, key = "#couponId")
    @Transactional(readOnly = true)
    public Coupon getCoupon(long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));
    }
}
