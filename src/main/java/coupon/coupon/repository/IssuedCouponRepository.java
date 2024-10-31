package coupon.coupon.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface IssuedCouponRepository extends Repository<IssuedCouponEntity, Long> {

    IssuedCouponEntity save(IssuedCouponEntity issuedCoupon);

    // TODO: 테이블에 memberId 인덱스 추가 !
    @Query("""
            SELECT COUNT(i.id) FROM IssuedCouponEntity i
            WHERE i.memberId = :memberId AND i.couponId = :couponId
            """)
    int countIssuedByMemberId(long memberId, long couponId);

    @Query("""
            SELECT ic FROM IssuedCouponEntity ic
            WHERE ic.memberId = :memberId
            """)
    List<IssuedCouponEntity> findAllIssuedCouponByMemberId(long memberId);
}
