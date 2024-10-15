package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import coupon.CouponException;
import coupon.dto.CouponCreateRequest;
import coupon.entity.Category;
import coupon.entity.Coupon;
import coupon.repository.CouponRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponRepository couponRepository;

    @BeforeEach
    void setUp() {
        couponRepository.deleteAllInBatch();
    }

    @DisplayName("입력으로 쿠폰을 생성한다.")
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
        couponService.create(request);

        // then
        assertThat(couponRepository.findById(1L)).isPresent();
        Coupon coupon = couponRepository.findById(1L).get();
        assertAll(
                () -> assertThat(coupon.getName()).isEqualTo("name"),
                () -> assertThat(coupon.getDiscount()).isEqualTo(5000),
                () -> assertThat(coupon.getMinimumOrder()).isEqualTo(50000),
                () -> assertThat(coupon.getCategory()).isEqualTo(Category.FOOD),
                () -> assertThat(coupon.getStart()).isEqualTo(LocalDate.of(2024, 10, 11)),
                () -> assertThat(coupon.getEnd()).isEqualTo(LocalDate.of(2024, 10, 15))
        );
    }

    @DisplayName("id로 쿠폰을 찾는다.")
    @Test
    void read() {
        // given
        couponRepository.save(new Coupon(
                "name",
                5000,
                50000,
                Category.FOOD,
                LocalDate.of(2024, 10, 11),
                LocalDate.of(2024, 10, 15)
        ));

        // when
        Coupon coupon = couponService.read(1L);

        // then
        assertAll(
                () -> assertThat(coupon.getName()).isEqualTo("name"),
                () -> assertThat(coupon.getDiscount()).isEqualTo(5000),
                () -> assertThat(coupon.getMinimumOrder()).isEqualTo(50000),
                () -> assertThat(coupon.getCategory()).isEqualTo(Category.FOOD),
                () -> assertThat(coupon.getStart()).isEqualTo(LocalDate.of(2024, 10, 11)),
                () -> assertThat(coupon.getEnd()).isEqualTo(LocalDate.of(2024, 10, 15))
        );
    }

    @DisplayName("해당 id의 쿠폰이 없는 경우 예외가 발생한다.")
    @Test
    void read_notFound() {
        assertThatThrownBy(() -> couponService.read(1L))
                .isInstanceOf(CouponException.class);
    }
}
