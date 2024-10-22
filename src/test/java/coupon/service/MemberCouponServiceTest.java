package coupon.service;

import static coupon.service.CouponCache.COUPON_CACHE_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.MemberCoupon;
import coupon.domain.repository.CouponRepository;
import coupon.domain.repository.MemberCouponRepository;
import coupon.service.dto.MemberCouponDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

@SpringBootTest
class MemberCouponServiceTest {

    @Autowired
    MemberCouponService memberCouponService;

    @Autowired
    CouponCache couponCache;

    @Autowired
    CacheManager cacheManager;

    @Autowired
    MemberCouponRepository memberCouponRepository;

    @Autowired
    private CouponRepository couponRepository;

    @BeforeEach
    void setUp() {
        memberCouponRepository.deleteAll();
        cacheManager.getCache(COUPON_CACHE_NAME).clear();
    }

    @DisplayName("회원 쿠폰을 생성한다.")
    @Test
    void create() {
        LocalDate now = LocalDate.now();
        Coupon coupon = new Coupon(1L, "coupon", 1000, 10000, now, now, Category.FASHION);
        couponCache.cache(coupon);
        MemberCoupon memberCoupon = new MemberCoupon(coupon.getId(), 1L, false, LocalDateTime.now());

        memberCouponRepository.save(memberCoupon);

        assertThat(memberCouponRepository.findById(memberCoupon.getId())).isNotNull();
    }

    @DisplayName("회원 쿠폰 생성 시 쿠폰이 없으면 예외가 발생한다.")
    @Test
    void memberCouponNotFound() {
        LocalDate now = LocalDate.now();
        Coupon coupon = new Coupon(1L, "coupon", 1000, 10000, now, now, Category.FASHION);
        MemberCoupon memberCoupon = new MemberCoupon(coupon.getId(), 1L, false, LocalDateTime.now());

        assertThatThrownBy(() -> memberCouponService.create(memberCoupon))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰이 존재하지 않습니다.");
    }

    @DisplayName("회원 쿠폰이 5장 이상일 때 생성하면 예외가 발생한다.")
    @Test
    void memberCouponIssueOutOfLimit() {
        LocalDate localDate = LocalDate.now();
        Coupon coupon = new Coupon(1L, "coupon", 1000, 10000, localDate, localDate, Category.FASHION);
        couponCache.cache(coupon);
        LocalDateTime localDateTime = LocalDateTime.now();
        List<MemberCoupon> memberCoupons = List.of(
                new MemberCoupon(coupon.getId(), 1L, false, localDateTime.minusDays(5)),
                new MemberCoupon(coupon.getId(), 1L, true, localDateTime),
                new MemberCoupon(coupon.getId(), 1L, false, localDateTime.plusDays(5)),
                new MemberCoupon(coupon.getId(), 1L, true, localDateTime.plusDays(1)),
                new MemberCoupon(coupon.getId(), 1L, false, localDateTime.minusDays(1))
        );
        memberCouponRepository.saveAll(memberCoupons);
        MemberCoupon memberCoupon = new MemberCoupon(coupon.getId(), 1L, false, localDateTime);

        assertThatThrownBy(() -> memberCouponService.create(memberCoupon))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("한 명의 회원은 동일한 쿠폰을 최대 5장까지 발급할 수 있습니다.");
    }

    @DisplayName("회원의 쿠폰 목록을 조회한다.")
    @Test
    void findByMemberId() throws InterruptedException {
        LocalDate localDate = LocalDate.now();
        Coupon savedCoupon = couponRepository.save(new Coupon("saved", 2000, 10000, localDate, localDate, Category.FOOD));
        Thread.sleep(2000);
        Coupon cachedCoupon = new Coupon(savedCoupon.getId() + 1, "cached", 1000, 20000, localDate, localDate, Category.FASHION);
        couponCache.cache(cachedCoupon);

        LocalDateTime localDateTime = LocalDateTime.of(2024, 10, 22, 12, 0, 0);
        MemberCoupon memberCoupon1 = memberCouponRepository.save(
                new MemberCoupon(cachedCoupon.getId(), 1L, false, localDateTime));
        MemberCoupon memberCoupon2 = memberCouponRepository.save(
                new MemberCoupon(savedCoupon.getId(), 1L, true, localDateTime));
        Thread.sleep(3000);

        List<MemberCouponDto> foundMemberCoupons = memberCouponService.findByMemberId(1L);

        assertThat(foundMemberCoupons).contains(
                MemberCouponDto.from(memberCoupon1, cachedCoupon),
                MemberCouponDto.from(memberCoupon2, savedCoupon)
        );
    }
}
