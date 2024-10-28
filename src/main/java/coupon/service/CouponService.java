package coupon.service;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.dto.CouponRequest;
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
    public Coupon create(CouponRequest couponRequest) {
        Category category = categoryRepository.findById(couponRequest.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        return couponRepository.save(couponRequest.toEntity(category));
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseGet(() -> replicationLagFallback.readFromWriter(() -> findById(couponId)));
    }

    private Coupon findById(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));
    }
}
