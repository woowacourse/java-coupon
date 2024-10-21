package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.Coupon;
import coupon.dto.SaveCouponRequest;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class CouponCommandServiceTest {

    @Autowired
    private CouponCommandService couponCommandService;

    @Autowired
    private CouponQueryService couponQueryService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    void tearDown() {
        jdbcTemplate.update("DELETE FROM coupon");
        jdbcTemplate.update("ALTER TABLE coupon AUTO_INCREMENT = 1");
    }

    @DisplayName("쿠폰을 저장한다.")
    @Test
    void save() {
        long savedCouponId = couponCommandService.save(new SaveCouponRequest(
                "천원 할인 쿠폰",
                1000,
                10000,
                LocalDate.now().minusDays(10),
                LocalDate.now().plusDays(10),
                "FOOD"));

        Coupon foundCoupon = couponQueryService.findById(savedCouponId);

        assertThat(foundCoupon.getName()).isEqualTo("천원 할인 쿠폰");
    }
}
