package coupon.service.coupon;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponRepository;
import coupon.exception.CouponException;
import coupon.support.TransactionSupport;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final TransactionSupport transactionSupport;

    public CouponService(CouponRepository couponRepository, TransactionSupport transactionSupport) {
        this.couponRepository = couponRepository;
        this.transactionSupport = transactionSupport;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "coupon", key = "#id")
    public Coupon getCoupon(Long id) {
        return couponRepository.findById(id)
                .orElseGet(() -> findCouponInWriteDataSource(id));
    }

    private Coupon findCouponInWriteDataSource(Long id) {
        return transactionSupport.executeNewTransaction(
                () -> couponRepository.findById(id)
                        .orElseThrow(() -> new CouponException("id(%s)에 해당하는 쿠폰이 존재하지 않습니다.".formatted(id))));
    }

    @Transactional
    @CachePut(value = "coupon", key = "#result.id")
    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }
}
