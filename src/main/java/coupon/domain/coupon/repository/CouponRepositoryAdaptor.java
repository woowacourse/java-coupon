package coupon.domain.coupon.repository;

import coupon.domain.coupon.Coupon;
import coupon.infra.db.CouponEntity;
import coupon.infra.db.jpa.JpaCouponRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryAdaptor implements CouponRepository {

    private final JpaCouponRepository jpaCouponRepository;

    @Override
    public Coupon save(Coupon coupon) {
        CouponEntity saved = jpaCouponRepository.save(toEntity(coupon));
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
        return jpaCouponRepository.findById(id).map(Coupon::from);
    }

    @Override
    public List<Coupon> findAllByIdIn(List<Long> couponIds) {
        return couponIds.stream()
                .map(this::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
}
