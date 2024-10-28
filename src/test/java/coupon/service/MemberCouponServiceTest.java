package coupon.service;

import coupon.entity.CouponEntity;
import coupon.entity.MemberEntity;
import coupon.repository.CouponRepository;
import coupon.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest
class MemberCouponServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    MemberCouponService memberCouponService;

    MemberEntity member;
    CouponEntity coupon;

    @BeforeEach
    void setUp() throws InterruptedException {
        member = memberRepository.save(new MemberEntity(1L, "카피"));
        coupon = couponRepository.save(new CouponEntity(1000, 10000));
        Thread.sleep(2000);
    }

    @DisplayName("멤버가 쿠폰을 발급한다.")
    @Test
    void issue() {
        assertThatCode(() -> memberCouponService.issue(coupon.toCoupon().getId(), member.toMember()))
                .doesNotThrowAnyException();
    }
}
