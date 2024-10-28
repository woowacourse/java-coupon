package coupon.repository;

import coupon.domain.PublishedCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublishedCouponRepository extends JpaRepository<PublishedCoupon, Long> {

    List<PublishedCoupon> findAllByMemberIdAndCouponId(Long memberId, Long couponId);
}
