package coupon.service;

import coupon.domain.Coupon;
import coupon.repository.CouponEntity;
import coupon.repository.CouponRepository;
import java.util.NoSuchElementException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional
@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public Long create(Coupon coupon) {
        CouponEntity savedCoupon = new CouponEntity(coupon);
        couponRepository.save(savedCoupon);
        return savedCoupon.getId();
    }

    @Transactional(readOnly = true)
    public CouponEntity getCoupon(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    public CouponEntity getCouponImmediately(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }
}
