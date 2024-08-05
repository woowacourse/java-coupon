package coupon.coupon.repository;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponStatus;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    List<Coupon> findAllByIssuableAndCouponStatusAndIssueStartedAtLessThanAndIssueEndedAtGreaterThan(boolean issuable,
                                                                                                     CouponStatus couponStatus,
                                                                                                     LocalDateTime issueStartedAt,
                                                                                                     LocalDateTime issueEndedAt);

    @Modifying
    @Query("update Coupon c set c.issueCount = :issueCount where c.id = :couponId")
    void updateIssueCount(@Param("couponId") Long couponId, @Param("issueCount") Long issueCount);

    @Modifying
    @Query("update Coupon c set c.useCount = :useCount where c.id = :couponId")
    void updateUseCount(@Param("couponId") Long couponId, @Param("useCount") Long useCount);
}
