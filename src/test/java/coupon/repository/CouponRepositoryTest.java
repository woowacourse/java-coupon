package coupon.repository;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.Category;
import coupon.domain.Coupon;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class CouponRepositoryTest {

    private static final String NAME = "default_name";
    private static final int MIN_ORDER_AMOUNT = 50_000;
    private static final int DISCOUNT_AMOUNT = 5_000;
    private static final Category CATEGORY = Category.FASHION;
    private static final LocalDate START_ISSUE_DATE = LocalDate.of(2024, 12, 1);
    private static final LocalDate END_ISSUE_DATE = LocalDate.of(2024, 12, 10);

    private static final Coupon COUPON = new Coupon(NAME, DISCOUNT_AMOUNT, MIN_ORDER_AMOUNT, CATEGORY, START_ISSUE_DATE,
            END_ISSUE_DATE);

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponMemoryRepository couponMemoryRepository;

    @Autowired
    private CouponDiskRepository couponDiskRepository;

    @Test
    void cashingTest() {
        couponRepository.save(COUPON);

        couponRepository.getById(COUPON.getId());

        Optional<Coupon> diskCoupon = couponDiskRepository.findById(COUPON.getId());
        Optional<Coupon> memoryCoupon = couponMemoryRepository.findById(COUPON.getId());
        assertThat(memoryCoupon).isEqualTo(diskCoupon);
    }
}
