package coupon.service;

import static coupon.domain.Category.FOOD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.repository.MemberRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CouponCommandServiceTest {


    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponCommandService couponCommandService;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void setUp() {
        couponRepository.deleteAll();
        memberCouponRepository.deleteAll();
    }

    @DisplayName("쿠폰을 발급 할 수 있다.")
    @Transactional
    @Test
    void issueCoupon() {
        Member savedMember = memberRepository.save(new Member());
        Coupon savedCoupon = couponRepository.save(
                new Coupon("쿠폰1", FOOD, 1_000, 10_000, LocalDateTime.now(), LocalDateTime.now().plusDays(1))
        );

        couponCommandService.issueCoupon(savedMember.getId(), savedCoupon.getId());

        assertThat(memberCouponRepository.count()).isOne();
    }

    @DisplayName("동일한 쿠폰은 5개까지만 발급 가능하다")
    @Test
    void issueByFive() {
        Member savedMember = memberRepository.save(new Member());
        Coupon savedCoupon = couponRepository.save(
                new Coupon("쿠폰1", FOOD, 1_000, 10_000, LocalDateTime.now(), LocalDateTime.now().plusDays(1))
        );

        couponCommandService.issueCoupon(savedMember.getId(), savedCoupon.getId());
        couponCommandService.issueCoupon(savedMember.getId(), savedCoupon.getId());
        couponCommandService.issueCoupon(savedMember.getId(), savedCoupon.getId());
        couponCommandService.issueCoupon(savedMember.getId(), savedCoupon.getId());
        couponCommandService.issueCoupon(savedMember.getId(), savedCoupon.getId());

        assertThatThrownBy(() -> couponCommandService.issueCoupon(savedMember.getId(), savedCoupon.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("동일한 쿠폰은 사용한 쿠폰 포함해서 5개까지만 발급 가능합니다.");
    }
}
