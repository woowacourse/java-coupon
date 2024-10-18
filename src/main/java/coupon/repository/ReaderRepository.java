package coupon.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.coupon.Coupon;

@Repository
public class ReaderRepository {

    private final CouponRepository couponRepository;

    public ReaderRepository(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Coupon> findById(long id) {
        return couponRepository.findById(id);
    }
}
