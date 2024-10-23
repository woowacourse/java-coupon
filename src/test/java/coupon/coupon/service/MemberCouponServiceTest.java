package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponRepository;
import coupon.coupon.domain.MemberCoupon;
import coupon.coupon.domain.MemberCouponRepository;
import coupon.coupon.dto.CouponResponse;
import coupon.coupon.dto.MemberCouponResponse;
import coupon.member.domain.Member;
import coupon.member.domain.MemberRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
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
        LocalDateTime localDateTime = LocalDateTime.of(2025, 1, 1, 0, 0);
        coupon = new Coupon(
                "Valid Coupon",
                1000,
                5000,
                Category.ELECTRONICS,
                localDateTime,
                localDateTime.plusDays(10)
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
                () -> memberCouponService.issueCoupon(savedCoupon.getId(), savedMember.getId(),
                        coupon.getStartDate().plusDays(1)))
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
                    new MemberCoupon(coupon.getId(), member.getId(), coupon.getStartDate().plusDays(1)));
        }

        // when & then
        assertThatThrownBy(
                () -> memberCouponService.issueCoupon(coupon.getId(), member.getId(),
                        coupon.getStartDate().plusDays(1)))
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
        assertAll(
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

    @DisplayName("회원이 발급받은 쿠폰 목록을 조회한다.")
    @Test
    void findMemberCouponsByMemberId() {
        // given
        coupon = couponRepository.save(coupon);
        member = memberRepository.save(member);

        MemberCoupon memberCoupon = memberCouponRepository.save(
                new MemberCoupon(coupon.getId(), member.getId(), coupon.getStartDate().plusDays(1)));

        // when
        List<MemberCouponResponse> memberCouponResponses =
                memberCouponService.findMemberCouponsByMemberId(member.getId());

        // then
        MemberCouponResponse memberCouponResponse = memberCouponResponses.get(0);
        assertAll(
                () -> assertThat(memberCouponResponse.id()).isEqualTo(memberCoupon.getId()),
                () -> assertThat(memberCouponResponse.couponResponse()).isEqualTo(CouponResponse.of(coupon)),
                () -> assertThat(memberCouponResponse.memberId()).isEqualTo(member.getId()),
                () -> assertThat(memberCouponResponse.used()).isFalse(),
                () -> assertThat(memberCouponResponse.issuedAt()).isEqualTo(memberCouponResponse.issuedAt()),
                () -> assertThat(memberCouponResponse.expiresAt()).isEqualTo(memberCouponResponse.expiresAt())
        );
    }
}
