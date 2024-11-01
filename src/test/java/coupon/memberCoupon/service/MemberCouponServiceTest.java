package coupon.memberCoupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;
import coupon.member.domain.Member;
import coupon.memberCoupon.dto.MemberCouponResponse;
import coupon.utils.IsolatedTest;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MemberCouponServiceTest extends IsolatedTest {

    @Autowired
    private MemberCouponService memberCouponService;

    @DisplayName("동일한 쿠폰을 5장 넘게 발급 시도 시 예외가 발생한다.")
    @Test
    void issueOverMaximumCount() {
        Member member = memberRepository.save(new Member());
        Coupon coupon = couponRepository.save(new Coupon());

        // 5장 발급
        memberCouponService.issueMemberCoupon(member.getId(), coupon.getId());
        memberCouponService.issueMemberCoupon(member.getId(), coupon.getId());
        memberCouponService.issueMemberCoupon(member.getId(), coupon.getId());
        memberCouponService.issueMemberCoupon(member.getId(), coupon.getId());
        memberCouponService.issueMemberCoupon(member.getId(), coupon.getId());

        // 6장째 발급 시도 시 예외 발생
        assertThatThrownBy(() -> memberCouponService.issueMemberCoupon(member.getId(), coupon.getId()))
                .isExactlyInstanceOf(IllegalStateException.class)
                .hasMessage("동일한 쿠폰은 최대 5장 까지만 발급할 수 있습니다.");
    }

    @DisplayName("회원에게 발급된 쿠폰의 정보를 쿠폰 정보와 함께 조회할 수 있다.")
    @Transactional
    @Test
    void findAllByMemberId() {
        // given
        Member member = memberRepository.save(new Member());
        Coupon coupon1 = couponRepository.save(
                new Coupon("coupon1", BigDecimal.valueOf(1000), BigDecimal.valueOf(10000),
                        Category.FOOD, LocalDateTime.now(), LocalDateTime.now().plusDays(7L)));
        Coupon coupon2 = couponRepository.save(
                new Coupon("coupon2", BigDecimal.valueOf(1000), BigDecimal.valueOf(10000),
                        Category.FOOD, LocalDateTime.now(), LocalDateTime.now().plusDays(7L)));

        // 1번 쿠폰 1회, 2번 쿠폰 2회 발급
        memberCouponService.issueMemberCoupon(member.getId(), coupon1.getId());
        memberCouponService.issueMemberCoupon(member.getId(), coupon2.getId());
        memberCouponService.issueMemberCoupon(member.getId(), coupon2.getId());

        Set<MemberCouponResponse> responses = memberCouponService.findAllMemberCouponsByMemberId(member.getId());

        assertThat(responses).hasSize(3);
    }
}
