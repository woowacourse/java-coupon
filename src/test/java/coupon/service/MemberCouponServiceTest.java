package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.support.CouponFixture;
import coupon.support.MemberFixture;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MemberCouponServiceTest extends BaseServiceTest {

    @Autowired
    MemberCouponService memberCouponService;

    Member tacan;
    Coupon chickenCoupon;


    @BeforeEach
    void setUp() {
        tacan = memberRepository.save(MemberFixture.create("tacan"));
        chickenCoupon = couponRepository.save(CouponFixture.createFoodCoupon("chicken"));
    }

    @Test
    void 멤버에게_쿠폰을_발급한다() {
        // when
        MemberCoupon memberCoupon = memberCouponService.create(tacan, chickenCoupon);

        // then
        Assertions.assertAll(
                () -> assertThat(memberCoupon.getCouponId()).isEqualTo(chickenCoupon.getId()),
                () -> assertThat(memberCoupon.getMemberId()).isEqualTo(tacan.getId()),
                () -> assertThat(memberCoupon.isUsed()).isFalse()
        );
    }

    @Test
    void 동일_쿠폰_발급_제한_초과시_예외가_발생한다() {
        // given
        for (int i = 0; i < 5; i++) {
            memberCouponRepository.save(MemberCoupon.issue(tacan.getId(), chickenCoupon.getId(), LocalDateTime.now()));
        }

        // when & then
        assertThatThrownBy(() -> memberCouponService.create(tacan, chickenCoupon))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
