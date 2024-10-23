package coupon.repository;

import coupon.domain.Coupon;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CouponWriterRepository implements CouponRepository {

    private final CouponDataSource couponDataSource;

    public CouponWriterRepository(CouponDataSource couponDataSource) {
        this.couponDataSource = couponDataSource;
    }

    @Override
    public Coupon save(Coupon coupon) {
        return couponDataSource.save(coupon);
    }

    @Override
    @Transactional
    public Optional<Coupon> findById(long id) {
        return couponDataSource.findById(id);
    }
}
