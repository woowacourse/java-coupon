package coupon.api.repository;

import coupon.entity.MemberCoupon;
import org.springframework.data.repository.Repository;

public interface MemberCouponRepository extends Repository<MemberCoupon, Long> {

    void save(MemberCoupon memberCoupon);
}
