package coupon.infrastructure;

import org.springframework.stereotype.Repository;

import coupon.domain.Coupon;
import coupon.domain.CouponRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CouponCoreRepository implements CouponRepository {

    private final CouponJpaRepository couponJpaRepository;

    @Override
    public Coupon save(Coupon coupon) {
        return couponJpaRepository.save(coupon);
    }

    @Override
    public Coupon findById(long id) {
        return couponJpaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("id에 해당하는 쿠폰이 존재하지 않습니다."));
    }
}
