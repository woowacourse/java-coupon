package coupon.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import coupon.domain.coupon.Coupon;

@Repository
public class ReaderRepository {

    private final CouponRepository couponRepository;

    public ReaderRepository(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public Optional<Coupon> findById(long id) {
        return couponRepository.findById(id);
    }
}
