package coupon.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponName;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    default Coupon fetchById(long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 쿠폰 정보입니다."));
    }

    default List<Coupon> fetchByCouponName(final String couponName) {
        return findByCouponName(new CouponName(couponName));
    }

    List<Coupon> findByCouponName(final CouponName couponName);
}
