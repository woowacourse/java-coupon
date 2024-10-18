package coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponWriter couponWriter;
    private final CouponReader couponReader;

    public void create(final Coupon coupon) {
        couponWriter.create(coupon);
    }

    public Coupon getCoupon(final UUID id) throws InterruptedException {
        Thread.sleep(2000);
        return couponReader.findById(id);
    }
}
