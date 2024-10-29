package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import coupon.Fixtures;
import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.repository.MemberRepository;

@SpringBootTest
@Transactional
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService sut;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;
    private Coupon coupon1;
    private Coupon coupon2;
    private Coupon coupon3;
    private Coupon coupon4;
    private Coupon coupon5;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(Fixtures.member_1);
        coupon1 = couponRepository.save(Fixtures.coupon_1);
        coupon2 = couponRepository.save(Fixtures.coupon_1);
        coupon3 = couponRepository.save(Fixtures.coupon_1);
        coupon4 = couponRepository.save(Fixtures.coupon_1);
        coupon5 = couponRepository.save(Fixtures.coupon_1);
        var memberCoupons = List.of(
                new MemberCoupon(member, coupon1),
                new MemberCoupon(member, coupon2),
                new MemberCoupon(member, coupon3),
                new MemberCoupon(member, coupon4),
                new MemberCoupon(member, coupon5));
        memberCouponRepository.saveAll(memberCoupons);
    }

    @Test
    @DisplayName("회원에게 쿠폰 발급 성공")
    void issue_coupon_success() {
        // given
        var member = memberRepository.save(Fixtures.member_2);
        var coupon = couponRepository.save(Fixtures.coupon_2);

        // when
        var actual = sut.issueMemberCoupon(member, coupon);

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("한 회원에게 5장이 넘는 쿠폰을 발급하면 예외 발생")
    void issue_coupon_fail() {
        var coupon6 = couponRepository.save(Fixtures.coupon_2);
        assertThatThrownBy(() -> sut.issueMemberCoupon(member, coupon6))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("한 회원에게 최대 5장까지 발급할 수 있습니다.");
    }

    @Test
    @DisplayName("회원의 쿠폰 목록 조회: 쿠폰의 정보를 모두 볼 수 있음")
    void findAllCouponByMember() {
        var actual = sut.findAllCouponByMember(member);
        assertAll(
                () -> assertThat(actual).hasSize(5),
                () -> assertThat(actual).containsExactlyInAnyOrder(coupon1, coupon2, coupon3, coupon4, coupon5)
        );
    }
}
