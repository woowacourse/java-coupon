package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import coupon.Fixtures;
import coupon.repository.CouponRepository;
import coupon.repository.MemberRepository;

@SpringBootTest
@Transactional
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService sut;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원에게 쿠폰 발급 성공")
    void issue_coupon_success() {
        // given
        var coupon = couponRepository.save(Fixtures.coupon);
        var member = memberRepository.save(Fixtures.member);

        // when
        var actual = sut.issueMemberCoupon(coupon, member);

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("한 회원에게 5장이 넘는 쿠폰을 발급하면 예외 발생")
    void issue_coupon_fail() {
        // given
        var coupon = couponRepository.save(Fixtures.coupon);
        var member = memberRepository.save(Fixtures.member);
        int maxCouponCount = 5;

        // when
        for (int i = 0; i < maxCouponCount; i++) {
            sut.issueMemberCoupon(coupon, member);
        }

        // then
        assertThatThrownBy(() -> sut.issueMemberCoupon(coupon, member))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("한 회원에게 최대 5장까지 발급할 수 있습니다.");
    }

}
