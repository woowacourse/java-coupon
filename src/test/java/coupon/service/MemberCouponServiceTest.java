package coupon.service;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponRepository;
import coupon.domain.coupon.MemberCoupon;
import coupon.domain.coupon.MemberCouponRepository;
import coupon.domain.member.Member;
import coupon.domain.member.MemberRepository;
import coupon.fixture.CouponFixture;
import coupon.fixture.MemberFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        memberCouponRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
        couponRepository.deleteAllInBatch();
    }

    @DisplayName("회원에게 쿠폰을 발급한다.")
    @Test
    void issueCouponTest() {
        // given
        Coupon coupon = CouponFixture.TEST_COUPON;
        Coupon savedCoupon = couponRepository.save(coupon);
        Member member = MemberFixture.TEST_MEMBER;
        Member savedMember = memberRepository.save(member);

        // when
        MemberCoupon memberCoupon = memberCouponService.create(savedCoupon.getId(), savedMember.getId());

        // then
        assertThat(memberCoupon.getId()).isNotNull();
    }

    @DisplayName("1인당 동일한 쿠폰은 사용된 것을 포함하여 최대 5개까지 발급할 수 있다.")
    @Test
    void couponLimitTest() {
        // given
        Coupon savedCoupon = couponRepository.save(CouponFixture.TEST_COUPON);
        Member savedMember = memberRepository.save(MemberFixture.TEST_MEMBER);
        IntStream.range(0, 5).forEach(i -> memberCouponService.create(savedCoupon.getId(), savedMember.getId()));

        // when & then
        long couponId = savedCoupon.getId();
        long memberId = savedMember.getId();
        assertThatThrownBy(() -> memberCouponService.create(couponId, memberId))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
