package coupon.application.membercoupon;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.application.coupon.CouponResponse;
import coupon.application.memer.MemberResponse;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponCategory;
import coupon.domain.coupon.CouponPeriod;
import coupon.domain.coupon.Name;
import coupon.domain.coupon.TestDiscountPolicy;
import coupon.domain.member.Member;
import coupon.domain.membercoupon.MemberCoupon;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCouponResponseTest {

    @Test
    @DisplayName("도메인의 값을 통해 객체를 생성한다.")
    void from() {
        MemberCoupon memberCoupon = memberCouponSample();

        MemberCouponResponse from = MemberCouponResponse.from(memberCoupon);

        MemberCouponResponse expected = new MemberCouponResponse(
                memberCoupon.getId(),
                CouponResponse.from(memberCoupon.getCoupon()),
                MemberResponse.from(memberCoupon.getMember()),
                memberCoupon.isUse(),
                "2024-11-01",
                "2024-11-03"
        );
        assertThat(from)
                .isEqualTo(expected);
    }

    private MemberCoupon memberCouponSample() {
        LocalDate startDate = LocalDate.of(2024, 11, 1);
        LocalDate endDate = LocalDate.of(2024, 11, 3);

        Coupon coupon = sampleCoupon(startDate, endDate);
        Member member = new Member(1L, "test@test.com", "password");
        return new MemberCoupon(
                1L,
                coupon,
                member,
                false,
                startDate,
                endDate
        );
    }

    private Coupon sampleCoupon(LocalDate startDate, LocalDate endDate) {
        Name name = new Name("테스트 쿠폰");
        TestDiscountPolicy discountPolicy = new TestDiscountPolicy(1000, 30000);

        CouponPeriod couponPeriod = new CouponPeriod(startDate, endDate);
        return new Coupon(1L, name, discountPolicy, CouponCategory.FASHION, couponPeriod);
    }
}
