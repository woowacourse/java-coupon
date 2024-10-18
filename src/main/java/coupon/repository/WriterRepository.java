package coupon.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.coupon.Coupon;

@Repository
public class WriterRepository {

    private final CouponRepository couponRepository;

    public WriterRepository(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Transactional
    public void save(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Transactional
    public Optional<Coupon> findById(long id) {
        return couponRepository.findById(id);
    }
}
