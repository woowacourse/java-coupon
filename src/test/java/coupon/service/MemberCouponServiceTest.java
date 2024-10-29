package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static coupon.domain.Category.FOOD;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.repository.MemberRepository;

@SpringBootTest
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService sut;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Nested
    class IssueMemberCoupon {

        @Test
        void success() {
            Coupon coupon = couponRepository.save(
                    new Coupon("name", 1000, 10000, FOOD, LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
            Member member = memberRepository.save(new Member("member"));

            var result = sut.issueMemberCoupon(member, coupon);

            assertThat(result.getCoupon().getId()).isEqualTo(coupon.getId());
            assertThat(result.getMember().getId()).isEqualTo(member.getId());
        }

        @Test
        void fail() {
            Coupon coupon = couponRepository.save(
                    new Coupon("name", 1000, 10000, FOOD, LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
            Member member = memberRepository.save(new Member("member"));
            for (int i = 0; i < 5; i++) {
                memberCouponRepository.save(new MemberCoupon(member, coupon));
            }

            assertThatThrownBy(() -> sut.issueMemberCoupon(member, coupon))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("동일한 쿠폰은 5장까지만 발급받을 수 있습니다.");
        }
    }
}
