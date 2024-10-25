package coupon.infrastructure.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponName;
import coupon.service.port.CouponRepository;

@Repository
public class CouponRepositoryImpl implements CouponRepository {

    private final CouponJpaRepository couponJpaRepository;

    public CouponRepositoryImpl(final CouponJpaRepository couponJpaRepository) {
        this.couponJpaRepository = couponJpaRepository;
    }

    @Override
    public Optional<Coupon> findByName(final CouponName name) {
        return couponJpaRepository.findByName(name.getValue())
                .map(CouponEntity::toDomain);
    }

    @Override
    public void save(final Coupon coupon) {
        final CouponEntity couponEntity = new CouponEntity(coupon);
        couponJpaRepository.save(couponEntity);
    }
}
