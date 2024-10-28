package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import coupon.domain.coupon.Coupon;
import coupon.repository.CouponRepository;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;
    @Autowired
    private TransactionHandler transactionHandler;

    @DisplayName("저장 후 바로 읽을 수 있다.")
    @Test
    void createAndGet() {
        Coupon coupon = new Coupon("쿠폰", 10000, 1000, "FOOD", LocalDate.now(), LocalDate.now());
        couponService.create(coupon);
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }

    @DisplayName("존재하지 않는 아이디의 쿠폰을 가져오면 예외가 발생한다.")
    @Test
    void getCouponWhenNotExist() {
        assertThatThrownBy(() -> couponService.getCoupon(1000L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 아이디의 쿠폰이 존재하지 않습니다.");
    }

    @DisplayName("쿠폰이 캐시에 없으면 DB에서 쿠폰을 가져오고 캐시에 저장한다.")
    @Test
    void getCouponWhenCacheMiss() {
        Cache<Long, Coupon> mockCache = mock(Cache.class);
        CouponRepository couponRepository = mock(CouponRepository.class);
        CouponService mockCouponService = new CouponService(couponRepository, transactionHandler, mockCache);
        Long couponId = 1L;
        Coupon coupon = new Coupon("쿠폰", 10000, 1000, "FOOD", LocalDate.now(), LocalDate.now());

        when(mockCache.get(couponId)).thenReturn(Optional.empty());
        when(couponRepository.findById(couponId)).thenReturn(Optional.of(coupon));

        Coupon result = mockCouponService.getCoupon(couponId);

        assertThat(result).isEqualTo(coupon);
        verify(couponRepository).findById(couponId);
        verify(mockCache).put(couponId, coupon);
    }

    @DisplayName("쿠폰이 캐시에 있으면 DB에 접근하지 않고 캐시에서 쿠폰을 가져온다.")
    @Test
    void getCouponWhenCacheHit() {
        Cache<Long, Coupon> mockCache = mock(Cache.class);
        CouponRepository couponRepository = mock(CouponRepository.class);
        CouponService mockCouponService = new CouponService(couponRepository, transactionHandler, mockCache);
        Long couponId = 1L;
        Coupon coupon = new Coupon("쿠폰", 10000, 1000, "FOOD", LocalDate.now(), LocalDate.now());

        when(mockCache.get(couponId)).thenReturn(Optional.of(coupon));

        Coupon result = mockCouponService.getCoupon(couponId);

        assertThat(result).isEqualTo(coupon);
        verify(mockCache, never()).put(anyLong(), any(Coupon.class));
        verify(couponRepository, never()).findById(couponId);
    }
}
