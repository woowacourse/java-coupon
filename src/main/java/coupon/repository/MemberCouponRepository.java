package coupon.repository;

import coupon.aspect.ImmediateRead;
import coupon.domain.MemberCoupon;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface MemberCouponRepository extends JpaRepository<MemberCoupon, Long> {

    @ImmediateRead
    @Transactional(readOnly = true)
    default Optional<MemberCoupon> findByIdImmediately(Long id) {
        return findById(id);
    }
}
