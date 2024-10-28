package coupon.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import coupon.domain.coupon.ProductCategory;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.repository.MemberRepository;
import coupon.repository.entity.Coupon;
import coupon.repository.entity.Member;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        memberCouponRepository.deleteAll();
        couponRepository.deleteAll();
        memberRepository.deleteAll();
        member = new Member("member_name");
        coupon = new Coupon("coupon_name",
                1000,
                10000,
                ProductCategory.패션,
                LocalDateTime.of(2024, 10, 18, 0, 0),
                LocalDateTime.of(2024, 10, 18, 0, 0).plusDays(5));
        memberRepository.save(member);
        couponRepository.save(coupon);
    }

    @Test
    void 쿠폰_발급() {
        // when & then
        assertDoesNotThrow(() -> memberCouponService.issueCoupon(member, coupon));
    }

    @Test
    void 쿠폰_발급_시_최대_개수_넘으면_예외_발생() {
        // when & then
        assertAll(
                () -> assertDoesNotThrow(() -> memberCouponService.issueCoupon(member, coupon)),
                () -> assertDoesNotThrow(() -> memberCouponService.issueCoupon(member, coupon)),
                () -> assertDoesNotThrow(() -> memberCouponService.issueCoupon(member, coupon)),
                () -> assertDoesNotThrow(() -> memberCouponService.issueCoupon(member, coupon)),
                () -> assertDoesNotThrow(() -> memberCouponService.issueCoupon(member, coupon)),
                () -> assertThatThrownBy(() -> memberCouponService.issueCoupon(member, coupon))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("한 명의 회원은 동일한 쿠폰을 최대 5장까지 발급할 수 있습니다.")
        );
    }
}
