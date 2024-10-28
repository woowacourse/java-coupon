package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.dto.CouponRequest;
import coupon.dto.MemberCouponRequest;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberCouponServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private MemberCouponService memberCouponService;

    @Test
    @DisplayName("사용자에게 발행된 모든 쿠폰 정보를 가져올 수 있다.")
    void getAllByMemberId() {
        Member member = memberService.createMember("리건");

        CouponRequest couponRequest = new CouponRequest("크리스마스 쿠폰", 1000, 10000, Category.FOODS);
        Coupon coupon = couponService.createCoupon(couponRequest);

        MemberCouponRequest memberCouponRequest = new MemberCouponRequest(coupon.getId(), member.getId(), false,
                LocalDateTime.now());
        memberCouponService.issueCoupon(memberCouponRequest);

        assertThat(memberCouponService.getIssuedMemberCoupons(member.getId()))
                .hasSize(1);
    }

    @Test
    @DisplayName("사용자와 쿠폰 정보를 가지고 사용자에게 쿠폰을 발행할 수 있다.")
    void issueCoupon() {
        Member member = memberService.createMember("리건");

        CouponRequest couponRequest = new CouponRequest("크리스마스 쿠폰", 1000, 10000, Category.FOODS);
        Coupon coupon = couponService.createCoupon(couponRequest);

        MemberCouponRequest memberCouponRequest = new MemberCouponRequest(coupon.getId(), member.getId(), false,
                LocalDateTime.now());

        assertDoesNotThrow(() -> memberCouponService.issueCoupon(memberCouponRequest));
    }

    @Test
    @DisplayName("쿠폰을 발행할 때, 정해진 최대 개수를 넘지 않았는지 검증한다.")
    void validIssueCoupon() {
        Member member = memberService.createMember("리건");

        CouponRequest couponRequest = new CouponRequest("크리스마스 쿠폰", 1000, 10000, Category.FOODS);
        Coupon coupon = couponService.createCoupon(couponRequest);

        MemberCouponRequest request = new MemberCouponRequest(coupon.getId(), member.getId(), false,
                LocalDateTime.now());

        for (int i = 0; i < 6; i++) {
            memberCouponService.issueCoupon(request);
        }

        assertThatThrownBy(() -> memberCouponService.issueCoupon(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 사용자에게 발행할 수 있는 쿠폰의 최대 개수를 초과했습니다.");
    }
}
