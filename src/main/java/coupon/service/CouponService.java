package coupon.service;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.dto.CouponRequest;
import coupon.dto.CouponResponse;
import coupon.repository.CategoryRepository;
import coupon.repository.CouponRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final CategoryRepository categoryRepository;

    public CouponService(CouponRepository couponRepository, CategoryRepository categoryRepository) {
        this.couponRepository = couponRepository;
        this.categoryRepository = categoryRepository;
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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));

        return CouponResponse.from(coupon);
    }
}
