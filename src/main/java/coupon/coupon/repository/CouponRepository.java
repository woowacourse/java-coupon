package coupon.coupon.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface CouponRepository extends Repository<CouponEntity, Long> {

    CouponEntity save(CouponEntity coupon);

    Optional<CouponEntity> findById(long id);

    default CouponEntity findByIdOrThrow(long id) {
        return findById(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 쿠폰입니다."));
    }

    // TODO: Coupon에 Date 인덱스 ?
    @Query("""
            SELECT c FROM CouponEntity c
            WHERE :currentDate >= c.issuableStartDate
            AND :currentDate < c.issuableEndDate
            """)
    List<CouponEntity> findAllIssuableCoupons(LocalDate currentDate);

    @Query("""
            SELECT c FROM CouponEntity c
            WHERE c.id IN :couponIds
            """)
    List<CouponEntity> findAllById(List<Long> couponIds);
}
