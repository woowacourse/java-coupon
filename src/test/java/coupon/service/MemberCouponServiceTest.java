package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDateTime;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    private Member member;
    private Coupon coupon;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(new Member("name", "email", "password"));
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(5);
        coupon = couponRepository.save(new Coupon("테스트 쿠폰", 1000, 5000, Category.FASHION, start, end));
    }

    @Test
    @DisplayName("쿠폰 발급 성공")
    @Transactional
    void testIssueCouponSuccess() {
        // when
        MemberCoupon memberCoupon = memberCouponService.issueCoupon(member.getId(), coupon.getId());

        // then
        assertAll(
                () -> assertThat(memberCoupon.getMember().getId()).isEqualTo(member.getId()),
                () -> assertThat(memberCoupon.getCoupon().getId()).isEqualTo(coupon.getId()),
                () -> assertThat(memberCouponRepository.countByMemberAndCoupon(member, coupon)).isEqualTo(1)
        );
    }

    @Test
    @DisplayName("쿠폰 발급 실패 : 최대 발급 제한 초과한 경우")
    @Transactional
    void testIssueCouponExceedsLimit() {
        // given
        for (int i = 0; i < 5; i++) {
            memberCouponRepository.save(new MemberCoupon(coupon, member));
        }

        // when & then
        assertThatThrownBy(() -> memberCouponService.issueCoupon(member.getId(), coupon.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("멤버 한 명당 동일한 쿠폰은 5장까지 발급 가능합니다.");
    }
}
