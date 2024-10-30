package coupon.repository;

import coupon.domain.Coupon;
import coupon.domain.IssuedCoupon;
import coupon.domain.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssuedCouponRepository extends JpaRepository<IssuedCoupon, Long> {

    List<IssuedCoupon> findAllByCouponIdAndMember(long couponId, Member member);

    List<IssuedCoupon> findAllByMemberId(long memberId);
}
