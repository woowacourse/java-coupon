package coupon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import coupon.domain.MemberCoupon;

@Repository
public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    List<MemberCoupon> findAllByMemberId(long id);

    int countByMemberIdAndCouponId(Long id, Long id1);
}
