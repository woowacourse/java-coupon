package coupon.member.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponName;
import coupon.coupon.domain.DiscountPercent;
import coupon.coupon.domain.DiscountPrice;
import coupon.coupon.domain.IssuePeriod;
import coupon.coupon.domain.MinimumOrderPrice;
import coupon.coupon.domain.entity.CouponEntity;
import coupon.coupon.repository.CouponRepository;
import coupon.member.domain.Member;
import coupon.member.repository.MemberRepository;

@SpringBootTest
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    @DisplayName("한 명의 회원은 동일한 쿠폰을 사용한 쿠폰을 포함하여 최대 5장까지 발급 가능")
    void createFail() {
        Coupon coupon = new Coupon(
                new CouponName("할인 쿠폰"),
                new DiscountPrice(1000),
                new MinimumOrderPrice(30000),
                new DiscountPercent(1000, 30000),
                Category.FOOD,
                new IssuePeriod(LocalDateTime.of(2024, 10, 17, 10, 10),
                        LocalDateTime.of(2024, 10, 18, 10, 10))
        );
        CouponEntity couponEntity = couponRepository.save(new CouponEntity(coupon));
        Member member = memberRepository.save(new Member("켬미"));
        memberCouponService.create(member, couponEntity);
        memberCouponService.create(member, couponEntity);
        memberCouponService.create(member, couponEntity);
        memberCouponService.create(member, couponEntity);
        memberCouponService.create(member, couponEntity);

        assertThatThrownBy(() -> memberCouponService.create(member, couponEntity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("회원 당 동일한 쿠폰을 사용한 쿠폰을 포함하여 최대 5장까지 발급할 수 있다.");
    }
}
