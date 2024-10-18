package coupon.infrastructure.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponName;
import coupon.domain.coupon.ProductionCategory;
import coupon.service.port.CouponRepository;

@SpringBootTest
class CouponRepositoryTest {

    @Autowired
    private CouponJpaRepository couponJpaRepository;
    @Autowired
    private CouponRepository couponRepository;

    @AfterEach
    void clear() {
        couponJpaRepository.deleteAll();
    }

    @DisplayName("쿠폰 객체가 입력되면 JpaRepository를 통해 저장한다.")
    @Test
    void save() {
        // Given
        final String couponName = "싱싱한 켈리 할인 쿠폰";
        final Coupon coupon = Coupon.create(
                couponName,
                10000,
                1000,
                ProductionCategory.FOOD,
                LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(3)
        );

        // When
        couponRepository.save(coupon);

        // Then
        final Coupon findCoupon = couponJpaRepository.findByName(couponName).get().toDomain();
        assertThat(findCoupon).isEqualTo(coupon);
    }

    @DisplayName("CouponName 객체가 입력되면 JpaRepository를 통해 일치하는 쿠폰을 찾아온다.")
    @Test
    void findByName() {
        // Given
        final String inputName = "싱싱한 켈리 할인 쿠폰";
        final Coupon coupon = Coupon.create(
                inputName,
                10000,
                1000,
                ProductionCategory.FOOD,
                LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(3)
        );
        final CouponEntity couponEntity = new CouponEntity(coupon);
        couponJpaRepository.save(couponEntity);

        // When
        final CouponName couponName = new CouponName(inputName);
        final Optional<Coupon> findCoupon = couponRepository.findByName(couponName);

        // Then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(findCoupon).isPresent();
            softly.assertThat(findCoupon.get()).isEqualTo(coupon);
        });
    }
}
