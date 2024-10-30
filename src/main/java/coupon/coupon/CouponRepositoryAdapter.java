package coupon.coupon;

import coupon.config.DataSourceContextHolder;
import coupon.config.DataSourceType;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryAdapter implements CouponRepository {

    private final CouponJpaRepository couponJpaRepository;
    private final TransactionTemplate transactionTemplate;

    @Override
    @CachePut(value = "coupon", key = "#coupon.id")
    public Coupon save(Coupon coupon) {
        return couponJpaRepository.save(coupon);
    }

    @Override
    @Cacheable(value = "coupon", key = "#couponId")
    public Optional<Coupon> findById(long couponId) {
        return couponJpaRepository.findById(couponId)
                .or(() -> getCouponByWriterDB(couponId));
    }

    private Optional<Coupon> getCouponByWriterDB(long couponId) {
        try {
            DataSourceContextHolder.setDataSource(DataSourceType.WRITER);
            transactionTemplate.setPropagationBehavior(TransactionTemplate.PROPAGATION_REQUIRES_NEW);
            return transactionTemplate.execute(status -> couponJpaRepository.findById(couponId));
        } finally {
            DataSourceContextHolder.clearDataSource();
        }
    }
}
