package coupon.domain.coupon.repository;

import coupon.domain.coupon.Coupon;
import coupon.infra.db.CouponEntity;
import coupon.infra.db.jpa.JpaCouponRepository;
import coupon.infra.db.redis.RedisCouponRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryAdaptor implements CouponRepository {

    private final JpaCouponRepository jpaCouponRepository;
    private final RedisCouponRepository redisCouponRepository;

    @Override
    public Coupon save(Coupon coupon) {
        CouponEntity saved = jpaCouponRepository.save(toEntity(coupon));
        redisCouponRepository.save(saved);
        return Coupon.from(saved);
    }

    private CouponEntity toEntity(Coupon coupon) {
        return new CouponEntity(
                coupon.getRawName(),
                coupon.getDiscountMoney(),
                coupon.getMinOrderMoney(),
                coupon.getRawCategory(),
                coupon.getStartDate(),
                coupon.getEndDate()
        );
    }

    @Override
    public Optional<Coupon> findById(Long id) {
        Optional<Coupon> coupon = redisCouponRepository.findById(id).map(Coupon::from);
        if (coupon.isEmpty()) {
            return jpaCouponRepository.findById(id).map(Coupon::from);
        }
        return coupon;
    }
}
