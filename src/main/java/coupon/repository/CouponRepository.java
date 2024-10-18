package coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import coupon.exception.CouponException;

public interface CouponRepository extends JpaRepository<CouponEntity, Long> {

    default CouponEntity fetchById(final long id) {
        return findById(id).orElseThrow(
                () -> new CouponException(String.format("%s는 존재하지 않는 쿠폰 id 입니다.", id)));
    }
}
