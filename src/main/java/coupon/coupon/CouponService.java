package coupon.coupon;

import coupon.datasource.DataSourceContextHolder;
import coupon.datasource.DataSourceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final TransactionTemplate transactionTemplate;
    private final CouponRepository couponRepository;

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(long couponId) {
        return couponRepository.findById(couponId)
                .orElseGet(() -> getCouponByWriterDB(couponId));
    }

    private Coupon getCouponByWriterDB(long couponId) {
        DataSourceContextHolder.setDataSource(DataSourceType.WRITER);
        transactionTemplate.setPropagationBehavior(TransactionTemplate.PROPAGATION_REQUIRES_NEW);
        return transactionTemplate.execute(status -> couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰을 찾을 수 없습니다. couponId : " + couponId)));
    }
}
