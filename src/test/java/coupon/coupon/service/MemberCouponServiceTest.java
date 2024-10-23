package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponRepository;
import coupon.coupon.domain.MemberCoupon;
import coupon.coupon.domain.MemberCouponRepository;
import coupon.member.domain.Member;
import coupon.member.domain.MemberRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
    private CouponRepository couponRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberCouponRepository memberCouponRepository;

    private Coupon coupon;
    private Member member;

    @BeforeEach
    void setUp() {
        coupon = new Coupon(
                "Valid Coupon",
                1000,
                5000,
                Category.ELECTRONICS,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7)
        );

        member = new Member("rush");
    }

    @AfterEach
    void tearDown() {
        memberCouponRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
        couponRepository.deleteAllInBatch();
    }

    @DisplayName("회원에게 쿠폰을 발급한다.")
    @Test
    void issueCoupon() {
        // given
        Coupon savedCoupon = couponRepository.save(coupon);
        Member savedMember = memberRepository.save(member);

        // when & then
        assertThatCode(
                () -> memberCouponService.issueCoupon(savedCoupon.getId(), savedMember.getId(), LocalDateTime.now()))
                .doesNotThrowAnyException();
    }

    @DisplayName("회원이 이미 최대 수량 쿠폰을 갖고 있을때, 쿠폰을 발급하면 예외가 발생한다.")
    @Test
    void exception_When_IssueCouponOverLimit() {
        // given
        coupon = couponRepository.save(coupon);
        member = memberRepository.save(member);
        for (int i = 0; i < 5; i++) {
            memberCouponRepository.save(
                    new MemberCoupon(coupon.getId(), member.getId(), LocalDateTime.now()));
        }

        // when & then
        assertThatThrownBy(
                () -> memberCouponService.issueCoupon(coupon.getId(), member.getId(), LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("장 이상의 쿠폰을 발급할 수 없습니다.");
    }

    @DisplayName("시작날짜보다 일찍 회원쿠폰을 발급할 수 없다.")
    @Test
    void exception_WhenIssuedOutsideValidPeriod() {
        // given
        coupon = couponRepository.save(coupon);
        member = memberRepository.save(member);

        // when & then
        Assertions.assertAll(
                () -> assertThatThrownBy(
                        () -> memberCouponService.issueCoupon(coupon.getId(), member.getId(),
                                coupon.getStartDate().minusDays(10)))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("시작일, 만료일"),
                () -> assertThatThrownBy(
                        () -> memberCouponService.issueCoupon(coupon.getId(), member.getId(),
                                coupon.getEndDate().plusDays(10)))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("시작일, 만료일")
        );
    }
}
