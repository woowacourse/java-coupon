package coupon.service;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.dto.CouponRequest;
import coupon.dto.CouponResponse;
import coupon.global.ReplicationLagFallback;
import coupon.repository.CategoryRepository;
import coupon.repository.CouponRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final CategoryRepository categoryRepository;
    private final ReplicationLagFallback replicationLagFallback;

    public CouponService(CouponRepository couponRepository,
                         CategoryRepository categoryRepository,
                         ReplicationLagFallback replicationLagFallback) {
        this.couponRepository = couponRepository;
        this.categoryRepository = categoryRepository;
        this.replicationLagFallback = replicationLagFallback;
    }

    @Transactional
    public CouponResponse create(CouponRequest couponRequest) {
        Category category = categoryRepository.findById(couponRequest.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        Coupon newCoupon = couponRepository.save(couponRequest.toEntity(category));

        return CouponResponse.from(newCoupon);
    }

    @Transactional(readOnly = true)
    public CouponResponse getCoupon(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseGet(() -> replicationLagFallback.readFromWriter(() -> findById(couponId)));
        return CouponResponse.from(coupon);
    }

    private Coupon findById(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));
    }
}
