package coupon.coupon.business;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.coupon.common.Fixture;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.IssuePeriod;
import coupon.coupon.persistence.CouponWriter;
import coupon.member.domain.Member;
import coupon.member.persistence.MemberWriter;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private MemberWriter memberWriter;

    @Autowired
    private CouponWriter couponWriter;

    @DisplayName("회원에게 쿠폰을 발급 시 발행 가능한 최대 개수를 초과하면 예외가 발생한다.")
    @Test
    void issueCouponFailByIssueCount() {
        Member member = memberWriter.create(Fixture.generateMember());
        IssuePeriod issuePeriod = new IssuePeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1L));
        Coupon coupon = couponWriter.create(Fixture.generateBigSaleFashionCoupon(issuePeriod));

        memberCouponService.issueCoupon(member.getId(), coupon.getId());
        memberCouponService.issueCoupon(member.getId(), coupon.getId());
        memberCouponService.issueCoupon(member.getId(), coupon.getId());
        memberCouponService.issueCoupon(member.getId(), coupon.getId());
        memberCouponService.issueCoupon(member.getId(), coupon.getId());

        assertThatThrownBy(() -> memberCouponService.issueCoupon(member.getId(), coupon.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("회원에게 쿠폰을 발급 시 발행 가능한 기간이 아닌 쿠폰일 경우 예외가 발생한다.")
    @Test
    void issueCouponFailByNotUsableCoupon() {
        Member member = memberWriter.create(Fixture.generateMember());
        IssuePeriod issuePeriod = new IssuePeriod(LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(2L));

        Coupon coupon = couponWriter.create(Fixture.generateBigSaleFashionCoupon(issuePeriod));

        assertThatThrownBy(() -> memberCouponService.issueCoupon(member.getId(), coupon.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("회원에게 쿠폰을 발급한다.")
    @Test
    void issueCoupon() {
        Member member = memberWriter.create(Fixture.generateMember());
        IssuePeriod issuePeriod = new IssuePeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1L));

        Coupon coupon = couponWriter.create(Fixture.generateBigSaleFashionCoupon(issuePeriod));

        assertThatCode(() -> memberCouponService.issueCoupon(member.getId(), coupon.getId()))
                .doesNotThrowAnyException();
    }
}
