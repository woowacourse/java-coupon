package coupon.repository;

import coupon.domain.MemberCoupon;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface MemberCouponRepository extends CrudRepository<MemberCoupon, UUID> {

    List<MemberCoupon> findByMemberIdAndCouponId(long memberId, UUID couponId);
}
