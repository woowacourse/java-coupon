package coupon.coupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.repository.CouponRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponServiceWithWriter couponServiceWithWriter;

    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long id) throws InterruptedException {
        try {
            return couponRepository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            return couponServiceWithWriter.getCoupon(id);
        }
    }
}
