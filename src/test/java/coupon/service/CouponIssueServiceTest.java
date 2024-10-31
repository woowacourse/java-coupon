package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.Category;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponName;
import coupon.domain.coupon.DiscountAmount;
import coupon.domain.coupon.IssueDuration;
import coupon.domain.coupon.MinimumOrderAmount;
import coupon.domain.member.Member;
import coupon.domain.member.MemberName;
import coupon.exception.CouponException;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.repository.MemberRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class CouponIssueServiceTest extends ServiceTest {

    @Autowired
    private CouponIssueService couponIssueService;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponRepository couponRepository;

    private Coupon savedCoupon;
    private Member savedMember;

    @BeforeEach
    void setup() {
        Member member = new Member(new MemberName("wiib"));
        Coupon coupon = new Coupon(
                new CouponName("쿠폰1"),
                new DiscountAmount("1000"),
                new MinimumOrderAmount("33333"),
                Category.ELECTRONICS,
                new IssueDuration(
                        LocalDateTime.of(2024, 1, 1, 1, 0, 0),
                        LocalDateTime.of(2025, 1, 1, 1, 0, 1)
                )
        );
        savedCoupon = couponRepository.save(coupon);
        savedMember = memberRepository.save(member);
    }

    @DisplayName("쿠폰을 발급한다.")
    @Test
    void issue1() {
        couponIssueService.issue(savedMember.getId(), savedCoupon.getId());

        int actual = memberCouponRepository.countByMemberIdAndCouponId(savedMember.getId(), savedCoupon.getId());

        assertThat(actual).isEqualTo(1);
    }

    @DisplayName("쿠폰 발급 가능 갯수를 초과하면 예외가 발생한다.")
    @Test
    void issue2() {
        for (int i = 0; i < 5; i++) {
            couponIssueService.issue(savedMember.getId(), savedCoupon.getId());
        }

        assertThatThrownBy(() -> couponIssueService.issue(savedMember.getId(), savedCoupon.getId()))
                .isInstanceOf(CouponException.class);
    }
}
