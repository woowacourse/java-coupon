package coupon.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import coupon.repository.CouponRepository;
import coupon.repository.entity.CouponEntity;

@SpringBootTest
class CouponServiceCachingTest {

    @Autowired
    private CouponService couponService;

    @MockBean
    private CouponRepository couponRepository;

    @DisplayName("쿠폰을 한 번 조회한 뒤, 다시 조회하면 캐싱된 결과를 반환한다.")
    @Test
    void get_caching_coupon() {
        // given
        final long id = 1;
        Optional<CouponEntity> couponEntity = Optional.of(new CouponEntity(
                "쿠폰",
                9000L,
                500L,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(2)
        ));
        when(couponRepository.findById(id))
                .thenReturn(couponEntity);

        // when
        couponService.getCoupon(id);
        couponService.getCoupon(id);

        // then
        verify(couponRepository, Mockito.times(1)).findById(id);
    }
}
