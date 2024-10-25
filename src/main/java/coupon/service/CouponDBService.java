package coupon.service;

import coupon.db.TransactionRouter;
import coupon.domain.coupon.Coupon;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class CouponDBService {

    private final CouponWriter couponWriter;
    private final CouponReader couponReader;
    private final TransactionRouter transactionRouter;

    public CouponDBService(CouponWriter couponWriter, CouponReader couponReader, TransactionRouter transactionRouter) {
        this.couponWriter = couponWriter;
        this.couponReader = couponReader;
        this.transactionRouter = transactionRouter;
    }

    public Coupon create(Coupon coupon) {
        return couponWriter.save(coupon);
    }

    public Coupon findById(long id) {
        return couponReader.findCoupon(id)
                .or(() -> transactionRouter.route(() -> couponReader.findCoupon(id)))
                .orElseThrow(() -> new NoSuchElementException("coupon with id " + id + " not found")); //reader에서 못찾으면 write db 조회
    }
}
