package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import coupon.CouponApplication;
import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.domain.repository.CouponRepository;
import coupon.domain.repository.MemberRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = CouponApplication.class)
class CouponServiceTest {

    @Autowired
    CouponService couponService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    CacheManager cacheManager;

    @Test
    void 복제지연테스트() {
        Coupon coupon = new Coupon(
                "Test Coupon",
                1000,
                30000,
                Category.FASHION,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                5
        );
        couponRepository.save(coupon);

        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }

    @Test
    @Transactional
    void 회원에게_쿠폰_발급_테스트() {
        Member member = new Member();
        memberRepository.save(member);

        Coupon coupon = new Coupon(
                "Test Coupon 2",
                1500,
                20000,
                Category.FOOD,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(10),
                10
        );
        couponRepository.save(coupon);

        couponService.issueCouponToMember(member.getId(), coupon.getId());

        // Writer DB로부터 직접 데이터를 확인하여 일관성을 보장합니다.
        List<MemberCoupon> memberCoupons = couponService.getMemberCouponsFromWriter(member);
        assertThat(memberCoupons).hasSize(1);
        assertThat(memberCoupons.get(0).getCouponId()).isEqualTo(coupon.getId());
    }

    @Test
    @Transactional
    void 쿠폰_발급_제한_테스트() {
        Member member = new Member();
        memberRepository.save(member);

        Coupon coupon = new Coupon(
                "Limited Coupon",
                2000,
                25000,
                Category.FASHION,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(5),
                10
        );
        couponRepository.save(coupon);

        for (int i = 0; i < 5; i++) {
            couponService.issueCouponToMember(member.getId(), coupon.getId());
        }

        List<MemberCoupon> memberCoupons = couponService.getMemberCouponsFromWriter(member);
        assertThat(memberCoupons).hasSize(5);
        assertThrows(IllegalArgumentException.class, () -> {
            couponService.issueCouponToMember(member.getId(), coupon.getId());
        });
    }

    @Test
    void 캐시_조회_테스트() {
        Coupon coupon = new Coupon(
                "Cached Coupon",
                3000,
                40000,
                Category.FASHION,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(15),
                20
        );
        couponRepository.save(coupon);

        Coupon firstFetch = couponService.getCoupon(coupon.getId());
        assertThat(firstFetch).isNotNull();

        Coupon cachedCoupon = (Coupon) cacheManager.getCache("coupons").get(coupon.getId()).get();
        assertThat(cachedCoupon).isNotNull();
        assertThat(cachedCoupon.getName()).isEqualTo("Cached Coupon");

        coupon.setName("Updated Cached Coupon");
        couponService.updateCoupon(coupon.getId(), coupon);

        Coupon updatedFetch = couponService.getCoupon(coupon.getId());
        assertThat(updatedFetch.getName()).isEqualTo("Updated Cached Coupon");
    }

    @Test
    @Transactional
    void 잘못된_회원_또는_쿠폰_ID_발급_테스트() {
        Member member = new Member();
        memberRepository.save(member);

        assertThrows(IllegalArgumentException.class, () -> {
            couponService.issueCouponToMember(member.getId(), 999L);
        });

        Coupon coupon = new Coupon(
                "Invalid Test Coupon",
                1500,
                20000,
                Category.FOOD,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(10),
                10
        );
        couponRepository.save(coupon);

        assertThrows(IllegalArgumentException.class, () -> {
            couponService.issueCouponToMember(999L, coupon.getId());
        });
    }

    @Test
    @Transactional
    void 쿠폰_수량_감소_테스트() {
        Member member = new Member();
        memberRepository.save(member);

        Coupon coupon = new Coupon(
                "Count Test Coupon",
                1500,
                20000,
                Category.FOOD,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(10),
                3
        );
        couponRepository.save(coupon);

        couponService.issueCouponToMember(member.getId(), coupon.getId());
        Coupon updatedCoupon = couponService.getCouponFromWriter(coupon.getId());
        assertThat(updatedCoupon.getAvailableCount()).isEqualTo(2);
    }
}
