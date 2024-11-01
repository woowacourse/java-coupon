package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import coupon.domain.coupon.Coupon;
import coupon.fixture.CouponFixture;
import coupon.service.db.CouponDBService;
import coupon.service.db.reader.CouponReader;
import coupon.service.db.writer.CouponWriter;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CouponDBServiceTest {

    private static final String CACHE_NAME = "coupon";

    @Autowired
    CouponDBService couponDBService;

    @Autowired
    CacheManager cacheManager;

    @MockBean
    CouponWriter couponWriter;

    @MockBean
    CouponReader couponReader;

    @BeforeEach
    void setUp(){
        Objects.requireNonNull(cacheManager.getCache(CACHE_NAME))
                .clear();
    }

    @DisplayName("쿠폰을 생성할 경우 cache에 적재된다")
    @Test
    void putCouponToCache_When_Created() {
        Coupon dummy = CouponFixture.COUPON_FIXTURE;
        when(couponWriter.save(any(Coupon.class))).thenReturn(dummy);

        couponDBService.create(dummy);
        Coupon actual = cacheManager.getCache(CACHE_NAME)
                .get(dummy.getId(), Coupon.class);

        assertThat(actual).usingRecursiveComparison().isEqualTo(dummy);
    }

    @DisplayName("쿠폰 조회 시, DB를 조회하기 전에 선제적으로 캐시를 조회한다")
    @Test
    void findCache_When_ReadCoupon() {
        Coupon dummy = CouponFixture.COUPON_FIXTURE;
        when(couponReader.findCoupon(dummy.getId())).thenReturn(Optional.of(dummy));

        Coupon firstFoundCoupon = couponDBService.findById(dummy.getId());
        Coupon secondFoundCoupon = couponDBService.findById(dummy.getId());
        Coupon cachedCoupon = cacheManager.getCache(CACHE_NAME)
                .get(dummy.getId(), Coupon.class);

        assertAll(
                () -> assertThat(firstFoundCoupon).usingRecursiveComparison().isEqualTo(dummy),
                () -> assertThat(secondFoundCoupon).usingRecursiveComparison().isEqualTo(dummy),
                () -> assertThat(cachedCoupon).usingRecursiveComparison().isEqualTo(dummy),
                () -> verify(couponReader, times(1)).findCoupon(eq(dummy.getId()))
        );
    }

    @DisplayName("reader에서 조회하지 못할 경우 write DB에서 조회한다")
    @Test
    void queryToWriter_When_Reader_CannotRead() {
        Coupon dummy = CouponFixture.COUPON_FIXTURE;
        when(couponReader.findCoupon(anyLong()))
                .thenReturn(Optional.empty(), Optional.of(dummy));

        Coupon actual = couponDBService.findById(1L);

        assertAll(
                () -> assertThat(actual).isEqualTo(dummy),
                () -> verify(couponReader, times(2)).findCoupon(1L)
        );
    }

    @DisplayName("writer를 통해 coupon을 생성한다")
    @Test
    void save_Through_Writer() {
        Coupon dummy = CouponFixture.COUPON_FIXTURE;
        when(couponWriter.save(any(Coupon.class)))
                .thenReturn(dummy);

        Coupon actual = couponDBService.create(dummy);

        assertAll(
                () -> assertThat(actual).isEqualTo(dummy),
                () -> verify(couponWriter, times(1)).save(dummy)
        );
    }
}
