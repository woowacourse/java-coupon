package coupon.service;

import coupon.domain.Coupon;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponWriteService couponWriteService;
    private final CouponReadService couponReadService;

    public long create(Coupon coupon) {
        return couponWriteService.create(coupon);
    }

    public Coupon read(Long id) {
        return couponReadService.readById(id).
                orElse(couponWriteService.readById(id));
    }
}
