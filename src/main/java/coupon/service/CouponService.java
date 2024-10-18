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

    /**
     * 이 메서드는 DB가 reader와 writer로 분리된 환경에서 복제 지연(Replication Lag) 때문에 readOnly=true를 사용하지 않았습니다. readOnly를 사용하면 Reader
     * DB에서만 조회하게 되어, Writer DB에 아직 반영되지 않은 최신 데이터를 조회하지 못할 수 있기 때문입니다.
     */
    @Transactional
    public CouponResponse getCoupon(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));

        return CouponResponse.from(coupon);
    }
}
