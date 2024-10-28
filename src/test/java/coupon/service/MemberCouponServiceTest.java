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

import coupon.ServiceSliceTest;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.repository.MemberRepository;

@SpringBootTest
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void init() {
        memberCouponService.deleteAll();
        memberService.deleteAll();
        couponService.deleteAll();
    }

//    @AfterEach
//    void tearDown() {
//        memberCouponService.deleteAll();
//        memberService.deleteAll();
//        couponService.deleteAll();
//    }

    @DisplayName("회원마다 가능한 수량 내에서 한 종류의 쿠폰을 여러 개 발급할 수 있다.")
    @Test
    void create() {
        // given
        Member member = new Member("sancho");
        memberService.create(member);
        Coupon coupon = new Coupon("create-test-coupon", 1000, 10000, LocalDate.now(), LocalDate.now(), Category.ELECTRONICS);
        couponService.create(coupon);

        // when
        memberCouponService.create(member, coupon.getId(), LocalDate.now());
        memberCouponService.create(member, coupon.getId(), LocalDate.now());
        memberCouponService.create(member, coupon.getId(), LocalDate.now());
        memberCouponService.create(member, coupon.getId(), LocalDate.now());
        long memberCouponId = memberCouponService.create(member, coupon.getId(), LocalDate.now());

        // then
        assertThat(memberCouponRepository.findById(memberCouponId)).isNotEmpty();
    }

    @DisplayName("가능한 수량을 초과하여 한 종류의 쿠폰을 발급하려고 하면 예외가 발생한다.")
    @Test
    void createFail() {
        // given
        Member member = new Member("sancho");
        memberService.create(member);
        Coupon coupon = new Coupon("create-fail-coupon", 1000, 10000, LocalDate.now(), LocalDate.now(), Category.ELECTRONICS);
        couponService.create(coupon);

        memberCouponService.create(member, coupon.getId(), LocalDate.now());
        memberCouponService.create(member, coupon.getId(), LocalDate.now());
        memberCouponService.create(member, coupon.getId(), LocalDate.now());
        memberCouponService.create(member, coupon.getId(), LocalDate.now());
        memberCouponService.create(member, coupon.getId(), LocalDate.now());

        // when & then
        assertThatThrownBy(() -> memberCouponService.create(member, coupon.getId(), LocalDate.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("모두 발급하셨기에 추가 발급이 불가능합니다.");
    }

    @DisplayName("회원의 쿠폰 목록을 조회한다.")
    @Test
    void getMemberCoupons() {
        // given
        Member member = new Member("sancho");
        memberService.create(member);
        System.out.println("##member = " + member);

        Coupon coupon = new Coupon("read-test-coupon", 1000, 10000, LocalDate.now(), LocalDate.now(), Category.ELECTRONICS);
        couponService.create(coupon);

        memberCouponService.create(member, coupon.getId(), LocalDate.now());
        memberCouponService.create(member, coupon.getId(), LocalDate.now());

        System.out.println("##size = " + memberCouponRepository.findAll().size());
        // when
        List<MemberCoupon> memberCoupons = memberCouponService.getMemberCoupons(member);
        System.out.println("###" + memberCoupons);

        // then
        assertThat(memberCoupons.size()).isEqualTo(2);
    }
}
