package coupon.service.db;

import coupon.db.TransactionRouter;
import coupon.domain.coupon.Coupon;
import coupon.service.db.reader.CouponReader;
import coupon.service.db.writer.CouponWriter;
import java.util.NoSuchElementException;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CouponDBService {

    private static final String CACHE_NAME = "coupon";

    private final CouponWriter couponWriter;
    private final CouponReader couponReader;
    private final TransactionRouter transactionRouter;

    public CouponDBService(CouponWriter couponWriter, CouponReader couponReader, TransactionRouter transactionRouter) {
        this.couponWriter = couponWriter;
        this.couponReader = couponReader;
        this.transactionRouter = transactionRouter;
    }

    @CachePut(value = CACHE_NAME, key = "#coupon.id")
    public Coupon create(Coupon coupon) {
        return couponWriter.save(coupon);
    }

    @Cacheable(value = CACHE_NAME, key = "#id")
    public Coupon findById(long id) {
        return couponReader.findCoupon(id)
                .or(() -> transactionRouter.route(() -> couponReader.findCoupon(id)))
                .orElseThrow(() -> new NoSuchElementException("coupon with id " + id + " not found")); //read db에서 못찾으면 write db 조회
    }
}
