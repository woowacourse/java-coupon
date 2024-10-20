package coupon.service;

import coupon.domain.Coupon;
import coupon.repository.CouponEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class CouponService {

    private final CouponWriter couponWriter;
    private final CouponReader couponReader;

    @Transactional
    public Long create(Coupon coupon) {
        return couponWriter.save(coupon);
    }

    @Transactional(readOnly = true)
    public CouponEntity getCoupon(Long id) {
        return couponReader.findById(id);
    }
}
