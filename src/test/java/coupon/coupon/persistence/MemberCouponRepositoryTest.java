package coupon.coupon.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import coupon.coupon.common.Fixture;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.IssuePeriod;
import coupon.coupon.domain.MemberCoupon;
import coupon.member.domain.Member;
import coupon.member.persistence.MemberRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class MemberCouponRepositoryTest {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @DisplayName("회원에게 발급된 쿠폰 목록을 조회한다.")
    @Test
    void findAllByMemberId() {
        Member member = memberRepository.save(Fixture.generateMember());
        IssuePeriod issuePeriod = new IssuePeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1L));
        Coupon coupon1 = couponRepository.save(Fixture.generateBigSaleFashionCoupon(issuePeriod));
        Coupon coupon2 = couponRepository.save(Fixture.generateBigSaleFashionCoupon(issuePeriod));
        memberCouponRepository.save(Fixture.generateMemberCoupon(member.getId(), coupon1.getId()));
        memberCouponRepository.save(Fixture.generateMemberCoupon(member.getId(), coupon2.getId()));

        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberId(member.getId());

        assertAll(
                () -> assertThat(memberCoupons).hasSize(2),
                () -> assertThat(memberCoupons).extracting(MemberCoupon::getCouponId)
                        .containsExactly(coupon1.getId(), coupon2.getId())
        );
    }

    @DisplayName("회원의 특정 쿠폰 발급 개수를 반환한다.")
    @Test
    void countByMemberIdAndCouponId() {
        Member member = memberRepository.save(Fixture.generateMember());
        IssuePeriod issuePeriod = new IssuePeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1L));
        Coupon coupon1 = couponRepository.save(Fixture.generateBigSaleFashionCoupon(issuePeriod));
        Coupon coupon2 = couponRepository.save(Fixture.generateBigSaleFashionCoupon(issuePeriod));
        memberCouponRepository.save(Fixture.generateMemberCoupon(member.getId(), coupon1.getId()));

        int issueCount = memberCouponRepository.countByMemberIdAndCouponId(member.getId(), coupon1.getId());

        assertThat(issueCount).isEqualTo(1);
    }
}
