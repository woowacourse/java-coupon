package coupon.service.membercoupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponCategory;
import coupon.domain.coupon.CouponRepository;
import coupon.domain.member.Member;
import coupon.domain.member.MemberRepository;
import coupon.domain.membercoupon.MemberCoupon;
import coupon.domain.membercoupon.MemberCouponRepository;
import coupon.exception.CouponException;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@EnableCaching
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponRepository couponRepository;

    private Member member;
    private Coupon coupon;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(new Member("member1"));
        coupon = couponRepository.save(createCoupon());
    }

    @Test
    void createMemberCouponTest() {
        MemberCoupon memberCoupon = memberCouponService.create(member, coupon);
        assertThat(memberCouponRepository.findById(memberCoupon.getId())).isPresent();
    }

    @Test
    void getMemberCouponTest() {
        MemberCoupon memberCoupon = memberCouponService.create(member, coupon);
        List<MemberCoupon> memberCoupons = memberCouponService.getMemberCoupons(member.getId());
        assertThat(memberCoupons).containsExactly(memberCoupon);
    }


    @Test
    void createMemberCouponOverFiveTest() {
        memberCouponService.create(member, coupon);
        memberCouponService.create(member, coupon);
        memberCouponService.create(member, coupon);
        memberCouponService.create(member, coupon);
        memberCouponService.create(member, coupon);

        assertThatThrownBy(() -> memberCouponService.create(member, coupon))
                .isInstanceOf(CouponException.class)
                .hasMessage("최대 5장까지 발급할 수 있습니다.");
    }

    private Coupon createCoupon() {
        return new Coupon(
                "쿠폰",
                1000,
                10000,
                CouponCategory.FOOD,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7)
        );
    }
}
