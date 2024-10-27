package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.repository.CouponRepository;
import coupon.repository.MemberRepository;

@SpringBootTest
public class CouponServiceTest {

    @Autowired
    private CouponService sut;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberRepository memberRepository;

    private final Coupon couponFixture = new Coupon(
            "쿠폰 이름",
            1000L,
            10000L,
            Category.FASHION,
            LocalDate.now(),
            LocalDate.now().plusDays(1L));

    private final Member memberFixture = new Member("초롱");

    @Test
    void 복제지연테스트() {
        // given
        var saved = sut.create(couponFixture);

        // when
        var actual = sut.getCoupon(saved.getId());

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("회원에게 쿠폰 발급 성공")
    void issue_coupon_success() {
        // given
        var coupon = couponRepository.save(couponFixture);
        var member = memberRepository.save(memberFixture);

        // when
        var actual = sut.issueMemberCoupon(coupon, member);

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("한 회원에게 5장이 넘는 쿠폰을 발급하면 예외 발생")
    void issue_coupon_fail() {
        // given
        var coupon = couponRepository.save(couponFixture);
        var member = memberRepository.save(memberFixture);
        int maxCouponCount = 5;

        // when
        for (int i = 0; i < maxCouponCount; i++) {
            sut.issueMemberCoupon(coupon, member);
        }

        // then
        assertThatThrownBy(() -> sut.issueMemberCoupon(couponFixture, memberFixture))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("한 회원에게 최대 5장까지 발급할 수 있습니다.");
    }
}
