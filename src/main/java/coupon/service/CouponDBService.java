package coupon.service;

import coupon.domain.Coupon;
import org.springframework.stereotype.Service;

@Service
public class CouponDBService {

    private final CouponWriter couponWriter;
    private final CouponReader couponReader;

    public CouponDBService(CouponWriter couponWriter, CouponReader couponReader) {
        this.couponWriter = couponWriter;
        this.couponReader = couponReader;
    }

    public Coupon create(Coupon coupon) {
        return couponWriter.save(coupon);
    }

    public Coupon findById(long id) {
        return couponReader.findCoupon(id)
                .orElseGet(() -> couponWriter.findCoupon(id)); //reader에서 못찾으면 write db 조회
    }
}
