package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.Category;
import coupon.repository.CouponEntity;
import coupon.repository.CouponRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    void 쿠폰을_생성한다() {
        // given
        CouponCreationRequest request = new CouponCreationRequest(
                "쿠폰", Category.FOOD,
                1000, 10000,
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31));

        // when
        CouponEntity created = couponService.create(request);

        // then
        assertThat(couponRepository.findById(created.getId())).isPresent();
    }

}
