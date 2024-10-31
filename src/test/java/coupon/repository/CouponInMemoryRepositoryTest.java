package coupon.repository;

import coupon.domain.Category;
import coupon.domain.Coupon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CouponInMemoryRepositoryTest {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponInMemoryRepository couponInMemoryRepository;

    @Test
    @DisplayName("쿠폰이 존재하면 캐시에서 해당 쿠폰을 가져올 수 있다.")
    void getCoupon() {
        // given
        LocalDateTime issuableFrom = LocalDateTime.of(2024, 8, 16, 0, 0, 0);
        LocalDateTime issuableTo = LocalDateTime.of(2024, 8, 17, 0, 0, 0);
        Coupon coupon = new Coupon("천원 할인 쿠폰", 1000, 5000, Category.FOOD, issuableFrom, issuableTo);
        couponRepository.save(coupon);
        couponInMemoryRepository.getCoupon(coupon.getId()); // cache

        // when
        couponRepository.delete(coupon);
        Coupon cachedCoupon = couponInMemoryRepository.getCoupon(coupon.getId());

        // then
        assertThat(cachedCoupon.getName()).isEqualTo("천원 할인 쿠폰");
    }

    @Test
    @DisplayName("쿠폰이 존재하지 않으면 예외를 던진다.")
    void getCoupon_notExist() {
        assertThatThrownBy(() -> couponInMemoryRepository.getCoupon(999_999_999L))
                .isInstanceOf(NoSuchElementException.class);
    }
}
