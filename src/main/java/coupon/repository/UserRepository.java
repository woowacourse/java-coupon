package coupon.repository;

import coupon.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT u.* FROM users u "
            + "LEFT JOIN user_coupon uc ON uc.user_id = u.id "
            + "GROUP BY u.id "
            + "HAVING COUNT(uc.id) <= :count ",
            nativeQuery = true)
    List<User> findAllByCouponCount(@Param("count") int count);
}
