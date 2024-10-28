package coupon.repository;

import coupon.repository.entity.Coupon;
import coupon.repository.entity.Member;
import coupon.repository.entity.MemberCoupon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    int countByMemberAndCoupon(Member member, Coupon coupon);

    List<MemberCoupon> findByMember(Member member);
}
