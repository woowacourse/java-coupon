package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.IssuedCoupon;
import coupon.domain.Member;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IssuedCouponServiceTest {

    @Autowired
    private IssuedCouponService issuedCouponService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CouponService couponService;

    @Test
    @DisplayName("쿠폰 발급 테스트")
    void issueTest() {
        //given
        Coupon coupon = couponService.create(
                new Coupon("name", 1000, 10000, Category.FASHION, LocalDate.now(), LocalDate.now().plusDays(7)));
        Member member = memberService.save(new Member());

        //when
        IssuedCoupon issue = issuedCouponService.issue(member.getId(), coupon.getId());

        //then
        assertThat(issue.getId()).isNotNull();
    }

    @Test
    @DisplayName("쿠폰은 다섯장까지 발급할 수 있다.")
    void getIssuedCouponsTest() {
        //given
        Coupon coupon = couponService.create(
                new Coupon("name", 1000, 10000, Category.FASHION, LocalDate.now(), LocalDate.now().plusDays(7)));
        Member member = memberService.save(new Member());

        //when
        issuedCouponService.issue(member.getId(), coupon.getId());
        issuedCouponService.issue(member.getId(), coupon.getId());
        issuedCouponService.issue(member.getId(), coupon.getId());
        issuedCouponService.issue(member.getId(), coupon.getId());
        issuedCouponService.issue(member.getId(), coupon.getId());

        //then
        assertThat(issuedCouponService.getIssuedCouponBy(member.getId()))
                .hasSize(5)
                .allMatch(issuedCouponResponse -> issuedCouponResponse.couponResponse().id() == coupon.getId());
    }

    @Test
    @DisplayName("쿠폰은 5장을 초과하여 발급할 수 없다.")
    void issueMaxTest() {
        //given
        Coupon coupon = couponService.create(
                new Coupon("name", 1000, 10000, Category.FASHION, LocalDate.now(), LocalDate.now().plusDays(7)));
        Member member = memberService.save(new Member());

        //when
        issuedCouponService.issue(member.getId(), coupon.getId());
        issuedCouponService.issue(member.getId(), coupon.getId());
        issuedCouponService.issue(member.getId(), coupon.getId());
        issuedCouponService.issue(member.getId(), coupon.getId());
        issuedCouponService.issue(member.getId(), coupon.getId());

        //then
        assertThatThrownBy(() -> issuedCouponService.issue(member.getId(), coupon.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
