package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import coupon.CouponException;
import coupon.dto.CouponCreateRequest;
import coupon.entity.Category;
import coupon.entity.Coupon;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @DisplayName("입력으로 쿠폰을 생성하고 찾는다.")
    @Test
    void create() {
        // given
        CouponCreateRequest request = new CouponCreateRequest(
                "name",
                5000,
                50000,
                Category.FOOD,
                LocalDate.of(2024, 10, 11),
                LocalDate.of(2024, 10, 15)
        );

        // when
        Coupon coupon = couponService.create(request);

        // then
        Coupon found = couponService.read(coupon.getId());
        assertAll(
                () -> assertThat(found.getName()).isEqualTo("name"),
                () -> assertThat(found.getDiscount()).isEqualTo(5000),
                () -> assertThat(found.getMinimumOrder()).isEqualTo(50000),
                () -> assertThat(found.getCategory()).isEqualTo(Category.FOOD),
                () -> assertThat(found.getStart()).isEqualTo(LocalDate.of(2024, 10, 11)),
                () -> assertThat(found.getEnd()).isEqualTo(LocalDate.of(2024, 10, 15))
        );
    }

    @DisplayName("해당 id의 쿠폰이 없는 경우 예외가 발생한다.")
    @Test
    void read_notFound() {
        assertThatThrownBy(() -> couponService.read(-1L))
                .isInstanceOf(CouponException.class);
    }
}
