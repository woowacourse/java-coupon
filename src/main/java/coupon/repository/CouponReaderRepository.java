package coupon.repository;

import coupon.domain.Coupon;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CouponReaderRepository implements CouponRepository {

    private final CouponDataSource couponDataSource;

    public CouponReaderRepository(CouponDataSource couponDataSource) {
        this.couponDataSource = couponDataSource;
    }

    @Override
    public Coupon save(Coupon coupon) {
        throw new RuntimeException("reader DB 에서 데이터를 저장할 수 없습니다.");
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Coupon> findById(long id) {
        return couponDataSource.findById(id);
    }
}
