package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.support.CouponFixture;
import coupon.support.MemberFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MemberCouponServiceTest extends BaseServiceTest {

    @Autowired
    MemberCouponService memberCouponService;

    Member tacan;


    @BeforeEach
    void setUp() {
        tacan = memberRepository.save(MemberFixture.create("tacan"));
    }

    @Test
    void 멤버에게_쿠폰을_발급한다() {
        // given
        Coupon chickenCoupon = couponRepository.save(CouponFixture.createFoodCoupon("chicken"));

        // when
        MemberCoupon memberCoupon = memberCouponService.create(tacan, chickenCoupon);

        // then
        Assertions.assertAll(
                () -> assertThat(memberCoupon.getCouponId()).isEqualTo(chickenCoupon.getId()),
                () -> assertThat(memberCoupon.getMemberId()).isEqualTo(tacan.getId()),
                () -> assertThat(memberCoupon.isUsed()).isFalse()
        );
    }
}
