package coupon.service;

import static coupon.domain.coupon.Category.FOOD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.entity.CouponEntity;
import coupon.entity.UserCouponEntity;
import coupon.entity.UserEntity;
import coupon.repository.CouponRepository;
import coupon.repository.UserCouponRepository;
import coupon.repository.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.cache.RedisCacheManager;

@SpringBootTest
class UserCouponServiceTest {

    @Autowired
    private UserCouponService userCouponService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Autowired
    private RedisCacheManager cacheManager;

    @BeforeEach
    void setUp() {
        userCouponRepository.deleteAll();
        userRepository.deleteAll();
        couponRepository.deleteAll();
        cacheManager.getCacheNames()
                .forEach(cacheName -> Objects.requireNonNull(cacheManager.getCache(cacheName)).clear());
    }

    private final UserEntity userFixture = new UserEntity("user1");
    private final CouponEntity couponFixture = new CouponEntity("coupon1", 1000, 10000,
            1, FOOD, LocalDate.now(), LocalDate.now().plusDays(7));

    @Test
    @DisplayName("사용자에게 쿠폰을 발급한다")
    void issueCoupon() {
        long userId = userRepository.save(userFixture).getId();
        long couponId = couponRepository.save(couponFixture).getId();

        UserCouponEntity issuedCoupon = userCouponService.issueCoupon(userId, couponId, LocalDateTime.now());

        assertThat(issuedCoupon).isNotNull();
        assertThat(cacheManager.getCache("newUserCoupon"))
                .isNotNull()
                .extracting(cache -> cache.get(issuedCoupon.getId()))
                .isNotNull();
    }

    @Test
    @DisplayName("사용자가 정해진 수 이상의 쿠폰을 발급받으려 하면, 예외를 발생한다.")
    void issueCoupon_ExceedMaxCount() {
        long userId = userRepository.save(userFixture).getId();
        long couponId = couponRepository.save(couponFixture).getId();

        for (int i = 0; i < 5; i++) {
            userCouponService.issueCoupon(userId, couponId, LocalDateTime.now());
        }

        assertThatThrownBy(() -> userCouponService.issueCoupon(userId, couponId, LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("발급받을 수 있는 쿠폰의 수를 초과했습니다.");
    }

    @Test
    @DisplayName("사용자의 쿠폰 목록을 조회한다")
    void getUserCoupons() {
        long userId = userRepository.save(userFixture).getId();
        long couponId = couponRepository.save(couponFixture).getId();
        userCouponService.issueCoupon(userId, couponId, LocalDateTime.now());

        assertThat(userCouponService.getUserCoupons(userId)).hasSize(1);
    }
}
