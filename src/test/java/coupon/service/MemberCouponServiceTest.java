package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;
import coupon.domain.member.Member;
import coupon.domain.membercoupon.MemberCoupon;
import coupon.service.dto.MemberCouponResponse;
import coupon.support.TransactionSupport;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MemberCouponServiceTest extends BaseServiceTest {

    @Autowired
    private TransactionSupport transactionSupport;

    @Autowired
    private MemberCouponService memberCouponService;

    private Member member;

    private Coupon coupon;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(new Member("alpaca"));
        coupon = couponRepository.save(
                new Coupon("알파카 털뭉치 할인", 1_000, 5_000, LocalDate.now(), LocalDate.now(), Category.FOOD));
    }

    @Test
    void 멤버_쿠폰을_발급한다() {
        // when
        memberCouponService.issue(member.getId(), coupon.getId());

        // then
        List<MemberCoupon> memberCoupons =
                memberCouponRepository.findByMemberIdAndCouponId(member.getId(), coupon.getId());
        assertThat(memberCoupons).hasSize(1);
    }

    @Test
    void 한_멤버가_동일한_쿠폰을_5장_초과해서_발급하면_예외가_발생한다() {
        // given
        for (int i = 0; i < 5; i++) {
            memberCouponRepository.save(MemberCoupon.issue(LocalDateTime.now(), member, coupon));
        }

        // when & then
        assertThatThrownBy(() -> memberCouponService.issue(member.getId(), coupon.getId()))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 5장의 쿠폰을 발급받았습니다.");
    }

    @Test
    void 멤버의_모든_멤버_쿠폰을_조회한다() {
        // given
        for (int i = 0; i < 3; i++) {
            memberCouponRepository.save(MemberCoupon.issue(LocalDateTime.now(), member, coupon));
        }

        // when
        List<MemberCouponResponse> responses = transactionSupport.executeWithWriter(
                () -> memberCouponService.findMemberCoupons(member.getId()));

        // then
        assertThat(responses).hasSize(3);
    }
}
