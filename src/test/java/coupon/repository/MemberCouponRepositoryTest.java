package coupon.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.fixture.CouponFixture;

@Transactional
@SpringBootTest
class MemberCouponRepositoryTest {

    @Autowired
    MemberCouponRepository memberCouponRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CouponRepository couponRepository;

    @Test
    void 회원에게_발급된_쿠폰_한_장을_저장한다() {
        // given
        Member member = new Member("test");
        Member savedMember = memberRepository.save(member);

        Coupon coupon = CouponFixture.create();
        Coupon savedCoupon = couponRepository.save(coupon);

        MemberCoupon memberCoupon = new MemberCoupon(savedCoupon.getId(), savedMember.getId(), LocalDateTime.now());

        // when
        MemberCoupon savedMemberCoupon = memberCouponRepository.save(memberCoupon);

        // then
        assertThat(savedMemberCoupon.isUsed()).isFalse();
        assertThat(savedMemberCoupon.getCouponId()).isEqualTo(savedCoupon.getId());
        assertThat(savedMemberCoupon.getMemberId()).isEqualTo(savedMember.getId());
    }
}
