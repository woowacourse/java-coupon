package coupon.coupon.service;

import org.springframework.stereotype.Service;

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

    public CouponEntity create(CouponRequest couponRequest) {
        Coupon coupon = new Coupon(
                new CouponName(couponRequest.name()),
                new DiscountPrice(couponRequest.discountPrice()),
                new MinimumOrderPrice(couponRequest.minimumOrderPrice()),
                new DiscountPercent(couponRequest.discountPrice(), couponRequest.minimumOrderPrice()),
                couponRequest.category(),
                new IssuePeriod(couponRequest.issuedAt(), couponRequest.expiresAt())
        );

        couponRepository.save(new CouponEntity(coupon));
    }
}
