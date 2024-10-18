package coupon.repository;

import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface CouponRepository extends Repository<CouponEntity, Long> {

    CouponEntity save(CouponEntity coupon);

    Optional<CouponEntity> findById(long id);

    default CouponEntity findByIdOrThrow(long id) {
        return findById(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 쿠폰입니다."));
    }
}
