package coupon.service;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.dto.CouponCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Test
    @DisplayName("복제 지연 현상이 일어난다.")
    void replication_delay() {
        //given
        CouponCreateRequest request = new CouponCreateRequest("test", 1000, 10000,
                Category.FASHION, LocalDate.now(), LocalDate.now().plusDays(1));
        Coupon coupon = couponService.create(request);

        //when, then
        assertThrows(IllegalArgumentException.class, () -> couponService.get(coupon.getId()));
    }
}
