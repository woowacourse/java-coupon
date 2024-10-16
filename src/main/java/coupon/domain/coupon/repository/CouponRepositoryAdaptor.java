package coupon.domain.coupon.repository;

import coupon.domain.coupon.Coupon;
import coupon.infra.db.CouponEntity;
import coupon.infra.db.JpaCouponRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class CouponRepositoryAdaptor implements CouponRepository {

    private final JpaCouponRepository couponDao;

    public CouponRepositoryAdaptor(JpaCouponRepository couponDao) {
        this.couponDao = couponDao;
    }

    @Override
    public Coupon save(Coupon coupon) {
        CouponEntity saved = couponDao.save(toEntity(coupon));
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
        return couponDao.findById(id).map(Coupon::from);
    }
}
