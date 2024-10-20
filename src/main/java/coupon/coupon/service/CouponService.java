package coupon.coupon.service;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponName;
import coupon.coupon.domain.DiscountPercent;
import coupon.coupon.domain.DiscountPrice;
import coupon.coupon.domain.IssuePeriod;
import coupon.coupon.domain.MinimumOrderPrice;
import coupon.coupon.domain.entity.CouponEntity;
import coupon.coupon.dto.request.CouponRequest;
import coupon.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    @CachePut(value = "couponCache", key = "#result.id")
    public CouponEntity create(CouponRequest couponRequest) {
        Coupon coupon = new Coupon(
                new CouponName(couponRequest.name()),
                new DiscountPrice(couponRequest.discountPrice()),
                new MinimumOrderPrice(couponRequest.minimumOrderPrice()),
                new DiscountPercent(couponRequest.discountPrice(), couponRequest.minimumOrderPrice()),
                couponRequest.category(),
                new IssuePeriod(couponRequest.issuedAt(), couponRequest.expiresAt())
        );

        return couponRepository.save(new CouponEntity(coupon));
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "couponCache", key = "#root.args[0]", unless = "#root.args[0] == null")
    public CouponEntity getCoupon(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID가 " + id + "인 쿠폰은 존재하지 않습니다."));
    }
}
