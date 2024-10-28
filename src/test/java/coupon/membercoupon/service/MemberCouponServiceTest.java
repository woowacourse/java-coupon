package coupon.membercoupon.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import coupon.coupon.domain.Coupon;
import coupon.coupon.service.CouponService;
import coupon.member.repository.MemberEntity;
import coupon.member.request.MemberCreateRequest;
import coupon.member.service.MemberService;
import java.util.UUID;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberCouponServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberCouponService memberCouponService;
    @Autowired
    private CouponService couponService;

    @DisplayName("회원별로 쿠폰은 5개를 넘게 발급할 수 없다.")
    @Test
    void validateMaxIssueCount() {
        // given
        String randomId = UUID.randomUUID().toString();
        MemberCreateRequest request = new MemberCreateRequest(randomId.substring(0, 5), "패스워드");
        Coupon coupon = new Coupon(1000L, 10000L);
        Long couponId = couponService.create(coupon);
        MemberEntity member = memberService.create(request);
        IntStream.range(0, 5).forEach(count -> memberCouponService.issueCoupon(member, couponId));

        // when & then
        assertThatThrownBy(() -> memberCouponService.issueCoupon(member, couponId))
                .isInstanceOf(RuntimeException.class);
    }
}
