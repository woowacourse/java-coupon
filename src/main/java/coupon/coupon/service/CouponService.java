package coupon.coupon.service;

import jakarta.persistence.Column;

import org.springframework.stereotype.Service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.repository.CouponRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public void create(final Coupon coupon) {
        couponRepository.save(coupon);
    }

    public Coupon getCoupon(final Long id) {
        return couponRepository.fetchById(id);
    }
}
