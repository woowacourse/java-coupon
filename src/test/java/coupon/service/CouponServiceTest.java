package coupon.service;

import coupon.domain.Coupon;
import coupon.repository.CouponRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CouponServiceTest {


    @Autowired
    private CouponRepository couponRepository;

    @Test
    @DisplayName("복제 지연 테스트")
    void lazyCopy() {
        // given
        List<Coupon> coupons = couponRepository.findAll();

        // when

        // then
        System.out.println("coupons = " + coupons);
    }
}
