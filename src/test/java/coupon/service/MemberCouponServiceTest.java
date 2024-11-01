package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;

@SpringBootTest
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private MemberService memberService;

    private Member member;
    private Coupon coupon;

    @BeforeEach
    void setUp() {
        member = new Member("sancho");
        coupon = new Coupon("test-coupon", 1000, 10000, LocalDate.now(), LocalDate.now(), Category.ELECTRONICS);

        memberService.create(member);
        couponService.create(coupon);
    }

    @AfterEach
    void tearDown() {
        memberCouponService.deleteAll();
        memberService.deleteAll();
        couponService.deleteAll();
    }

    @DisplayName("회원마다 가능한 수량 내에서 한 종류의 쿠폰을 여러 개 발급할 수 있다.")
    @Test
    void create() {
        // given
        for (int i = 0; i < 4; i++) {
            memberCouponService.create(member, coupon.getId(), LocalDate.now());
        }

        // when
        long memberCouponId = memberCouponService.create(member, coupon.getId(), LocalDate.now());

        // then
        assertThat(memberCouponService.findById(memberCouponId)).isNotEmpty();
    }

    @DisplayName("가능한 수량을 초과하여 한 종류의 쿠폰을 발급하려고 하면 예외가 발생한다.")
    @Test
    void createFail() {
        // given
        for (int i = 0; i < 5; i++) {
            memberCouponService.create(member, coupon.getId(), LocalDate.now());
        }

        // when & then
        assertThatThrownBy(() -> memberCouponService.create(member, coupon.getId(), LocalDate.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("모두 발급하셨기에 추가 발급이 불가능합니다.");
    }

    @DisplayName("회원의 쿠폰 목록을 조회한다.")
    @Test
    void getMemberCoupons() {
        // given
        memberCouponService.create(member, coupon.getId(), LocalDate.now());
        memberCouponService.create(member, coupon.getId(), LocalDate.now());

        // when
        List<MemberCoupon> memberCoupons = memberCouponService.getMemberCoupons(member);

        // then
        assertThat(memberCoupons.size()).isEqualTo(2);
    }
}
