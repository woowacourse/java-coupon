package coupon.membercoupon.business;

import coupon.coupon.exception.CouponException;
import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.fixture.MemberCouponFixture;
import coupon.membercoupon.infrastructure.MemberCouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @BeforeEach
    void setUp() {
        memberCouponRepository.deleteAll();
    }

    @DisplayName("회원 쿠폰 발급에 성공한다.")
    @Test
    void issueSuccess() {
        // given
        memberCouponRepository.save(MemberCouponFixture.getMemberCoupon());
        memberCouponRepository.save(MemberCouponFixture.getMemberCoupon());
        memberCouponRepository.save(MemberCouponFixture.getMemberCoupon());
        memberCouponRepository.save(MemberCouponFixture.getMemberCoupon());

        // when
        memberCouponService.issue(1L, 1L);

        // then
        List<MemberCoupon> memberCoupons = memberCouponRepository.findByCouponIdAndMemberId(1L, 1L);
        assertThat(memberCoupons).hasSize(5);
    }

    @DisplayName("회원 쿠폰 발급 횟수가 초과되어 발급에 실패한다.")
    @Test
    void issueFail() {
        // given
        memberCouponRepository.save(MemberCouponFixture.getMemberCoupon());
        memberCouponRepository.save(MemberCouponFixture.getMemberCoupon());
        memberCouponRepository.save(MemberCouponFixture.getMemberCoupon());
        memberCouponRepository.save(MemberCouponFixture.getMemberCoupon());
        memberCouponRepository.save(MemberCouponFixture.getMemberCoupon());

        // when & then
        assertThatThrownBy(() ->memberCouponService.issue(1L, 1L))
                .isInstanceOf(CouponException.class);
    }
}
