package coupon.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.dto.CouponRequest;
import coupon.repository.CategoryRepository;
import coupon.repository.CouponRepository;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class MockCouponServiceTest {

    @MockBean
    CouponRepository mockCouponRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CouponService mockCouponService;

    @Test
    @DisplayName("조회 시 캐싱 테스트")
    void getCouponWithCacheTest() {
        // given
        Category category = categoryRepository.findById(1L).get();
        CouponRequest couponRequest = new CouponRequest(
                "쿠폰1",
                10000L,
                2000L,
                1L,
                LocalDate.now(),
                LocalDate.now().plusDays(7));

        Coupon coupon = new Coupon(
                1L,
                "쿠폰1",
                10000L,
                2000L,
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                category);

        when(mockCouponRepository.save(any()))
                .thenReturn(coupon);
        when(mockCouponRepository.findById(any()))
                .thenReturn(Optional.of(coupon));

        mockCouponService.create(couponRequest);
        mockCouponService.getCoupon(coupon.getId());

        verify(mockCouponRepository, never()).findById(coupon.getId());
    }
}
