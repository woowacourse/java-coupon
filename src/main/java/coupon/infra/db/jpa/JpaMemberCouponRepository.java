package coupon.infra.db.jpa;

import coupon.infra.db.MemberCouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberCouponRepository extends JpaRepository<MemberCouponEntity, Long> {
}
