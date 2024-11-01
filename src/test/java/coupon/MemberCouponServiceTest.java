package coupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.List;
import coupon.cleaner.DatabaseCleanerExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(DatabaseCleanerExtension.class)
@SpringBootTest
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private CouponService couponService;

    @DisplayName("회원 쿠폰은 5장까지 발급 가능하다.")
    @Test
    void issueSuccess() {
        Member member = new Member(1L, "회원");
        Coupon coupon = new Coupon("test", 1000, Category.APPLIANCES, 10000);
        couponService.create(coupon);

        memberCouponService.issue(coupon.getId(), member);
        memberCouponService.issue(coupon.getId(), member);
        memberCouponService.issue(coupon.getId(), member);
        memberCouponService.issue(coupon.getId(), member);
        memberCouponService.issue(coupon.getId(), member);

        List<MemberCouponResponse> memberCoupons = memberCouponService.getMemberCoupons(coupon.getId(), member.getId());
        assertThat(memberCoupons.size()).isEqualTo(5);
    }

    @DisplayName("회원 쿠폰을 5장 초과하여 발급하면 에러가 발생한다.")
    @Test
    void issueFail() {
        Member member = new Member(1L, "회원");
        Coupon coupon = new Coupon("test", 1000, Category.APPLIANCES, 10000);
        couponService.create(coupon);

        memberCouponService.issue(coupon.getId(), member);
        memberCouponService.issue(coupon.getId(), member);
        memberCouponService.issue(coupon.getId(), member);
        memberCouponService.issue(coupon.getId(), member);
        memberCouponService.issue(coupon.getId(), member);

        assertThatCode(() -> memberCouponService.issue(coupon.getId(), member))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("발급한 회원 쿠폰을 조회하면 쿠폰과 회원 쿠폰들이 담겨있다.")
    @Test
    void getMemberCoupons() {
        Member member = new Member(1L, "회원");
        Coupon coupon = new Coupon("test", 1000, Category.APPLIANCES, 10000);
        couponService.create(coupon);

        memberCouponService.issue(coupon.getId(), member);
        memberCouponService.issue(coupon.getId(), member);
        memberCouponService.issue(coupon.getId(), member);
        memberCouponService.issue(coupon.getId(), member);
        memberCouponService.issue(coupon.getId(), member);

        List<MemberCouponResponse> memberCouponsResponse = memberCouponService.getMemberCoupons(coupon.getId(), member.getId());

        assertThat(memberCouponsResponse.stream()
                .allMatch(response -> response.coupon().getId().equals(coupon.getId()) &&
                                      response.memberCoupon().getMemberId().equals(member.getId()))).isTrue();
    }
}
