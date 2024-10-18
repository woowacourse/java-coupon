package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.Coupon;
import coupon.exception.CouponException;
import coupon.repository.CouponRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class CouponQueryServiceTest {

    @Autowired
    private CouponQueryService couponQueryService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    void tearDown() {
        jdbcTemplate.update("DELETE FROM coupon");
        jdbcTemplate.update("ALTER TABLE coupon AUTO_INCREMENT = 1");
    }

    @DisplayName("성공: 존재하는 ID로 조회")
    @Test
    void findById() {
        Coupon saved = couponRepository.save(new Coupon("천원 할인 쿠폰", 1000, 10000,
                LocalDate.now().minusDays(5), LocalDate.now().plusDays(5), "FOOD"));

        Coupon found = couponQueryService.findById(saved.getId());

        assertThat(saved.getId()).isEqualTo(found.getId());
    }

    @DisplayName("실패: 존재하지 않는 ID로 조회")
    @Test
    void findById_Fail() {
        assertThatThrownBy(() -> couponQueryService.findById(-1L))
                .isInstanceOf(CouponException.class);
    }
}
