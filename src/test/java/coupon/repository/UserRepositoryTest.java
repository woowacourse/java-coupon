package coupon.repository;

import coupon.domain.User;
import coupon.domain.UserCoupon;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserCouponRepository userCouponRepository;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userCouponRepository.deleteAll();
        userRepository.deleteAll();
    }

    @DisplayName("발급 가능한 쿠폰 갯수를 넘는 유저는 조회하지 않는다.")
    @Test
    void findAllByCouponCount_NotSelected() {
        // given
        User user = userRepository.save(new User("zeze"));
        userCouponRepository.save(new UserCoupon(1L, user, true, LocalDateTime.now(), LocalDateTime.now().plusDays(7)));
        userCouponRepository.save(new UserCoupon(2L, user, true, LocalDateTime.now(), LocalDateTime.now().plusDays(7)));

        // when
        List<User> users = userRepository.findAllByCouponCount(1);

        // then
        Assertions.assertThat(users).isEmpty();
    }

    @DisplayName("발급 가능한 쿠폰 갯수를 넘지 않는 유저를 조회한다.")
    @Test
    void findAllByCouponCount() {
        // given
        User user = userRepository.save(new User("zeze"));
        userCouponRepository.save(new UserCoupon(1L, user, true, LocalDateTime.now(), LocalDateTime.now().plusDays(7)));
        userCouponRepository.save(new UserCoupon(2L, user, true, LocalDateTime.now(), LocalDateTime.now().plusDays(7)));

        // when
        List<User> users = userRepository.findAllByCouponCount(2);

        // then
        Assertions.assertThat(users).contains(user);
    }
}
