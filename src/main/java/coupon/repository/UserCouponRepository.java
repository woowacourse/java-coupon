package coupon.repository;

import coupon.entity.UserCouponEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCouponRepository extends JpaRepository<UserCouponEntity, Long> {

    List<UserCouponEntity> findAllByUserId(long userId);
}
