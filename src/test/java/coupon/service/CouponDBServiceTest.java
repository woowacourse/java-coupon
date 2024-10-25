package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import coupon.domain.coupon.Coupon;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CouponDBServiceTest {

    @Autowired
    CouponDBService couponDBService;

    @MockBean
    CouponWriter couponWriter;

    @MockBean
    CouponReader couponReader;

    @DisplayName("reader에서 조회하지 못할 경우 write DB에서 조회한다")
    @Test
    void queryToWriter_When_Reader_CannotRead() {
        Coupon dummy = mock(Coupon.class);
        when(couponReader.findCoupon(anyLong())).thenReturn(Optional.empty(), Optional.of(dummy));

        Coupon actual = couponDBService.findById(1L);

        assertAll(
                () -> assertThat(actual).isEqualTo(dummy),
                () -> verify(couponReader, times(2)).findCoupon(1L)
        );
    }

    @DisplayName("writer를 통해 coupon을 생성한다")
    @Test
    void save_Through_Writer() {
        Coupon dummy = mock(Coupon.class);
        when(couponWriter.save(any(Coupon.class))).thenReturn(dummy);

        Coupon actual = couponDBService.create(dummy);

        assertAll(
                () -> assertThat(actual).isEqualTo(dummy),
                () -> verify(couponWriter, times(1)).save(dummy)
        );
    }
}
