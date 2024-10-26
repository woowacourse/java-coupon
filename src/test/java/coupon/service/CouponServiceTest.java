package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.Category;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponName;
import coupon.domain.coupon.DiscountMount;
import coupon.domain.coupon.MinimumMount;
import coupon.domain.coupon.Period;
import coupon.domain.member.Member;
import coupon.domain.member.MemberName;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.repository.MemberRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private MemberCouponRepository memberCouponRepository;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void truncateTables() {
        memberCouponRepository.deleteAll();
        couponRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void 복제지연테스트() {
        Coupon coupon = couponService.create(new Coupon(
                new CouponName("쿠폰"),
                new DiscountMount(1000),
                new MinimumMount(5000),
                Category.FASHION,
                new Period(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1))
        ));
        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }

    @DisplayName("존재하지 않는 쿠폰으로는 멤버의 쿠폰을 발급할 수 없다.")
    @Test
    void issueMemberCouponWithoutCoupon() {
        Member member = memberRepository.save(new Member(new MemberName("포케리스웨트")));

        Long notExistId = 2L;
        couponRepository.deleteById(notExistId);
        assertThatThrownBy(() -> couponService.issueMemberCoupon(notExistId, member))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 쿠폰 id입니다.");
    }

    @DisplayName("같은 쿠폰을 한 멤버가 5번 넘게 발급할 수 없다.")
    @Test
    void issueMemberCouponMoreThanLimit() {
        Member member = memberRepository.save(new Member(new MemberName("포케리스웨트")));
        Coupon coupon = couponService.create(new Coupon(
                new CouponName("쿠폰"),
                new DiscountMount(1000),
                new MinimumMount(5000),
                Category.FASHION,
                new Period(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1))
        ));

        for (int i = 0; i < 5; ++i) {
            couponService.issueMemberCoupon(coupon.getId(), member);
        }
        assertThatThrownBy(() -> couponService.issueMemberCoupon(coupon.getId(), member))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰을 더 발급할 수 없습니다.");
    }
}
