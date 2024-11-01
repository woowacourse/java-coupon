package coupon.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import coupon.member.domain.MemberCoupon;

@Repository
public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    @Query("SELECT mc.couponEntity.id FROM MemberCoupon mc WHERE mc.member.id = :memberId")
    List<Long> findAllIdByMemberId(@Param("memberId") Long memberId);
}
