package coupon.service;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import coupon.domain.Coupon;
import coupon.dto.request.CouponSaveRequest;
import coupon.repository.CouponRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @SpyBean
    private CacheManager cacheManager;

    @SpyBean
    private CouponRepository couponRepository;

    @Test
    void 복제지연테스트() {
        CouponSaveRequest request = new CouponSaveRequest("행운쿠폰", 1000L, 30000L, "패션", LocalDateTime.now(),
                LocalDateTime.now().plusDays(1));
        Coupon saved = couponService.save(request);

        assertThatCode(() -> couponService.findById(saved.getId()))
                .doesNotThrowAnyException();
    }

    @Test
    void 쿠폰_발행_시_캐시에_저장한다() {
        CouponSaveRequest request = new CouponSaveRequest("반짝 쿠폰", 1000L, 30000L, "가전", LocalDateTime.now(),
                LocalDateTime.now().plusDays(1));

        Coupon dbCoupon = couponService.save(request);
        Cache couponCache = cacheManager.getCache("coupon");

        assertAll(
                () -> assertThat(couponCache).isNotNull(),
                () -> assertThat(requireNonNull(couponCache).get(dbCoupon.getId(), Coupon.class))
                        .extracting(Coupon::getId)
                        .isEqualTo(dbCoupon.getId())
        );
    }

    @Test
    void 쿠폰_조회_시_캐시를_사용한다() {
        CouponSaveRequest request = new CouponSaveRequest("반짝 쿠폰", 1000L, 30000L, "가전", LocalDateTime.now(),
                LocalDateTime.now().plusDays(1));
        Coupon dbCoupon = couponService.save(request);

        Cache spyCache = spy(requireNonNull(cacheManager.getCache("coupon")));
        when(cacheManager.getCache("coupon")).thenReturn(spyCache);

        Coupon coupon = couponService.findById(dbCoupon.getId());

        assertAll(
                () -> assertThat(coupon.getId()).isEqualTo(dbCoupon.getId()),
                () -> verify(spyCache, times(1)).get(dbCoupon.getId()),
                () -> verify(couponRepository, never()).findById(dbCoupon.getId())
        );
    }
}
