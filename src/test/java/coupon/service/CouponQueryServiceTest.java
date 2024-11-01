package coupon.service;


import static coupon.domain.Category.FOOD;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import coupon.domain.Coupon;
import coupon.repository.CouponRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CouponQueryServiceTest {

    @Autowired
    private CacheManager cacheManager;

    @SpyBean
    CouponRepository couponRepository;

    @Autowired
    CouponQueryService couponQueryService;

    @BeforeEach
    void setUp() {
        cacheManager.getCache("coupon").clear();
    }

    @DisplayName("쿠폰을 1번 조회 후 두번째 조회부터는 캐시에서 데이터를 조회한다.")
    @Test
    void get_coupon_in_cache() throws InterruptedException {
        // given
        Coupon coupon = new Coupon("쿠폰1", FOOD, 1_000, 10_000, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        given(couponRepository.findById(1L))
                .willReturn(Optional.of(coupon));

        // when
        couponQueryService.getCoupon(1L); // 첫번 째 호출
        couponQueryService.getCoupon(1L); // 두번 째 호출

        // then
        verify(couponRepository, times(1)).findById(1L);
    }
}
