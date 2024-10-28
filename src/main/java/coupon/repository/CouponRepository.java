package coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import coupon.domain.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    default Coupon fetchById(Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException(id + "에 해당하는 쿠폰을 찾을 수 없습니다."));
    }
}
