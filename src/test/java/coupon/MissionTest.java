package coupon;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.ProductionCategory;
import coupon.infrastructure.repository.CouponJpaRepository;
import coupon.service.CouponService;
import coupon.service.dto.request.CouponCreateRequest;

@SpringBootTest
class MissionTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponJpaRepository couponJpaRepository;

    @AfterEach
    public void clear() {
        couponJpaRepository.deleteAll();
    }

    @DisplayName("복제 지연 테스트")
    @Test
    void 복제_지연_테스트() {
        // Given
        final CouponCreateRequest request = new CouponCreateRequest(
                "싱싱한 켈리 할인 쿠폰",
                10000,
                1000,
                ProductionCategory.FOOD,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7)
        );

        // When
        couponService.createCoupon(request);
        final Coupon findCoupon = couponService.findByName("싱싱한 켈리 할인 쿠폰");

        // Then
        assertThat(findCoupon).isNotNull();
    }
}
