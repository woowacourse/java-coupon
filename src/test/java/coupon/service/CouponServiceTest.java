package coupon.service;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.dto.CouponCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    private CouponCreateRequest request;
    private LocalDate startDate;

    @BeforeEach
    void setUp() {
        startDate = LocalDate.of(2024, 10, 18);
        request = new CouponCreateRequest("test", 1000, 10000,
                Category.FASHION, startDate, startDate.plusDays(1));
    }

    @Test
    @DisplayName("쿠폰을 생성할 수 있다.")
    void create() {
        //when
        Coupon coupon = couponService.create(request);

        //then
        assertAll(
                () -> assertThat(coupon.getName()).isEqualTo("test"),
                () -> assertThat(coupon.getCategory()).isEqualTo(Category.FASHION),
                () -> assertThat(coupon.getStartDate()).isEqualTo(startDate),
                () -> assertThat(coupon.getEndDate()).isEqualTo(startDate.plusDays(1))
        );
    }

    @Disabled
    @Test
    @DisplayName("복제 지연 현상이 일어난다.")
    void get_replication_delay() {
        //given
        Coupon coupon = couponService.create(request);

        //when, then
        assertThrows(IllegalArgumentException.class, () -> couponService.get(coupon.getId()));
    }

    @Test
    @DisplayName("aop 적용 이후 복제 지연 현상이 일어나지 않는다.")
    void get_replication_delay_covered() {
        //given
        Coupon coupon = couponService.create(request);

        //when
        Coupon foundCoupon = couponService.get(coupon.getId());

        //then
        assertThat(foundCoupon).isNotNull();
    }
}
