package coupon.service;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.dto.MemberCouponResponse;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

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
    void setUp() throws InterruptedException {
        member = memberRepository.save(new Member("name", "email", "password"));
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(5);
        coupon = couponRepository.save(new Coupon("쿠폰", 1000, 5000, Category.FASHION, start, end));
    }

    @Test
    @DisplayName("쿠폰 발급 성공")
    void testIssueCouponSuccess() {
        // when
        MemberCoupon memberCoupon = memberCouponService.issueCoupon(member.getId(), coupon.getId());

        // then
        assertAll(
                () -> assertThat(memberCoupon.getMember().getId()).isEqualTo(member.getId()),
                () -> assertThat(memberCouponRepository.countByMemberAndCouponId(member, coupon.getId())).isEqualTo(1)
        );
    }

    @Test
    @DisplayName("쿠폰 발급 실패 : 최대 발급 제한 초과한 경우")
    void testIssueCouponExceedsLimit() {
        // given
        for (int i = 0; i < 5; i++) {
            memberCouponRepository.save(new MemberCoupon(coupon.getId(), member));
        }

        // when & then
        assertThatThrownBy(() -> memberCouponService.issueCoupon(member.getId(), coupon.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("멤버 한 명당 동일한 쿠폰은 5장까지 발급 가능합니다.");
    }

    @Test
    @DisplayName("회원의 쿠폰 목록 조회 성공")
    void testReadMemberCouponsByMember_Success() {
        // given
        memberCouponRepository.save(new MemberCoupon(coupon.getId(), member));

        // when
        List<MemberCouponResponse> coupons = memberCouponService.readMemberCouponsByMember(member.getId());

        // then
        assertAll(
                () -> assertThat(coupons).hasSize(1),
                () -> assertThat(coupons.get(0).couponName()).isEqualTo("쿠폰"),
                () -> assertThat(coupons.get(0).memberName()).isEqualTo("name")
        );
    }

    @Test
    @DisplayName("회원의 쿠폰이 없는 경우 빈 목록 반환")
    void testReadMemberCouponsByMember_Empty() {
        // when
        List<MemberCouponResponse> coupons = memberCouponService.readMemberCouponsByMember(member.getId());

        // then
        assertThat(coupons).isEmpty();
    }
}
