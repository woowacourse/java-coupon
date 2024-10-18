package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.ProductionCategory;
import coupon.mock.FakeCouponRepository;
import coupon.mock.FakeTransactionExecutor;
import coupon.service.dto.request.CouponCreateRequest;

class CouponServiceTest {

    private CouponService couponService;
    private FakeCouponRepository couponRepository;

    @BeforeEach
    void setUp() {
        couponRepository = new FakeCouponRepository();
        couponService = new CouponService(
                couponRepository, new FakeTransactionExecutor<Coupon>()
        );
    }

    @DisplayName("쿠폰 생성 요청이 입력되면 쿠폰 객체를 생성해 저장소에 저장한다.")
    @Test
    void createdCoupon() {
        // Given
        final String couponName = "싱싱한 켈리 할인 쿠폰";
        final CouponCreateRequest request = new CouponCreateRequest(
                couponName,
                10000,
                1000,
                ProductionCategory.FOOD,
                LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(3)
        );

        // When
        couponService.createCoupon(request);

        // Then
        final Coupon savedCoupon = couponRepository.getLast();
        assertThat(savedCoupon).isNotNull();
    }

    @DisplayName("쿠폰 이름으로 특정 쿠폰을 조회한다.")
    @Test
    void findByName() {
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
        couponRepository.addItem(coupon);

        // When
        final Coupon findCoupon = couponService.findByName(couponName);

        // Then
        assertSoftly(softly -> {
            softly.assertThat(findCoupon).isNotNull();
            softly.assertThat(findCoupon).isEqualTo(coupon);
        });
    }

    @DisplayName("존재하지 않은 쿠폰 이름으로 쿠폰을 조회 요청이 입력되면 예외를 발생시킨다.")
    @Test
    void findByNameInputNotExistCouponName() {
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
        couponRepository.addItem(coupon);

        // When & Then
        final String notExistCouponName = "싱싱한 도라 할인 쿠폰";
        assertThatThrownBy(() -> couponService.findByName(notExistCouponName))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("입력된 쿠폰 이름에 일치하는 쿠폰 정보가 존재하지 않습니다. -" + notExistCouponName);
    }
}
