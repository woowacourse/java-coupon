package coupon.membercoupon.repository;

import coupon.member.repository.MemberEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCouponEntity, Long> {
    List<MemberCouponEntity> findByMemberEntity(MemberEntity memberEntity);
}
