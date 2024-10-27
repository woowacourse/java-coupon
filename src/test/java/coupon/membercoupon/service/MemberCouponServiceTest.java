package coupon.membercoupon.service;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import coupon.CouponException;
import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;
import coupon.coupon.repository.CouponRepository;
import coupon.member.domain.Member;
import coupon.member.repository.MemberRepository;
import coupon.membercoupon.repository.MemberCouponRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
class MemberCouponServiceTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberCouponRepository memberCouponRepository;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private MemberCouponService memberCouponService;

    @DisplayName("사용자에게 쿠폰을 발급한다.")
    @Test
    void issueCoupon() {
        // given
        LocalDate startAt = LocalDate.now();
        LocalDate endAt = LocalDate.now().plusDays(6);
        Coupon coupon = new Coupon("coupon", 1000, 10000, Category.FASHION, startAt, endAt);
        Member member = new Member("Prin!");
        couponRepository.save(coupon);
        memberRepository.save(member);

        // when
        memberCouponService.issue(member, coupon);

        // then
        assertThat(memberCouponRepository.findAllByMemberIdAndCouponId(member.getId(), coupon.getId())).hasSize(1);
    }

    @DisplayName("한 회원이 5장 초과로 쿠폰 발급을 시도하면 예외가 발생한다.")
    @Test
    void cannotIssueCouponIfExceedCount() {
        // given
        LocalDate startAt = LocalDate.now();
        LocalDate endAt = LocalDate.now().plusDays(6);
        Coupon coupon = new Coupon("coupon", 1000, 10000, Category.FASHION, startAt, endAt);
        Member member = new Member("Prin?!");
        couponRepository.save(coupon);
        memberRepository.save(member);
        for (int count = 1; count <= 5; count++) {
            memberCouponService.issue(member, coupon);
        }

        // when & then
        assertThatThrownBy(() -> memberCouponService.issue(member, coupon))
                .isInstanceOf(CouponException.class)
                .hasMessage("동일한 쿠폰을 5장 이상 발급할 수 없어요.");
    }
}
