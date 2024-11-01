package coupon.coupon.domain;

import coupon.member.domain.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    int countByOwnerAndCouponId(Member owner, Long coupon);

    List<MemberCoupon> findMemberCouponByOwner(Member member);
}
