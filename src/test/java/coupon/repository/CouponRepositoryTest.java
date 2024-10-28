package coupon.repository;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.Coupon;
import coupon.domain.CouponFixture;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class CouponRepositoryTest {

    private static final Coupon COUPON = CouponFixture.CREATED_COUPON;

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
