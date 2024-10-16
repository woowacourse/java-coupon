package coupon.domain.coupon.repository;

import coupon.domain.coupon.Coupon;
import coupon.infra.db.CouponCache;
import coupon.infra.db.CouponEntity;
import coupon.infra.db.JpaCouponRepository;
import coupon.infra.db.RedisCouponRepository;
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
        redisCouponRepository.save(toCache(saved));
        return Coupon.from(saved);
    }

    private CouponCache toCache(CouponEntity couponEntity) {
        return new CouponCache(
                couponEntity.getId(),
                couponEntity.getName(),
                couponEntity.getDiscountMoney(),
                couponEntity.getMinOrderMoney(),
                couponEntity.getCategory(),
                couponEntity.getStartDate(),
                couponEntity.getEndDate()
        );
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
        Optional<Coupon> coupon = redisCouponRepository.findById(id).map(Coupon::fromCache);
        if (coupon.isEmpty()) {
            return jpaCouponRepository.findById(id).map(Coupon::from);
        }
        return coupon;
    }
}
