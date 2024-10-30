package coupon.application.membercoupon;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import coupon.application.membercoupon.exception.TooManyMemberCouponException;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponCategory;
import coupon.domain.coupon.CouponPeriod;
import coupon.domain.coupon.Name;
import coupon.domain.coupon.TestDiscountPolicy;
import coupon.domain.coupon.repository.CouponRepository;
import coupon.domain.member.Member;
import coupon.domain.member.repository.MemberRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberCouponWriteServiceTest {

    @Autowired
    private MemberCouponWriteService memberCouponWriteService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    @DisplayName("같은 쿠폰은 5장까지만 발급할 수 있다.")
    void addCouponToMember() {
        LocalDate startDate = LocalDate.of(2024, 11, 1);
        LocalDate endDate = LocalDate.of(2024, 11, 3);
        Member member = sampleMember();
        Coupon coupon = sampleCoupon(startDate, endDate);

        assertDoesNotThrow(() -> memberCouponWriteService.addCouponToMember(coupon.getId(), member.getId()));
        assertDoesNotThrow(() -> memberCouponWriteService.addCouponToMember(coupon.getId(), member.getId()));
        assertDoesNotThrow(() -> memberCouponWriteService.addCouponToMember(coupon.getId(), member.getId()));
        assertDoesNotThrow(() -> memberCouponWriteService.addCouponToMember(coupon.getId(), member.getId()));
        assertDoesNotThrow(() -> memberCouponWriteService.addCouponToMember(coupon.getId(), member.getId()));

        assertThatThrownBy(() -> memberCouponWriteService.addCouponToMember(coupon.getId(), member.getId()))
                .isInstanceOf(TooManyMemberCouponException.class)
                .hasMessage("한 회원에게 같은 쿠폰은 최대 5장 발급할 수 있습니다.");
    }

    private Member sampleMember() {
        return memberRepository.save(new Member("test@test.com", "password"));
    }

    private Coupon sampleCoupon(LocalDate startDate, LocalDate endDate) {
        Name name = new Name("테스트 쿠폰");
        TestDiscountPolicy discountPolicy = new TestDiscountPolicy(1000, 30000);

        CouponPeriod couponPeriod = new CouponPeriod(startDate, endDate);
        return couponRepository.save(new Coupon(name, discountPolicy, CouponCategory.FASHION, couponPeriod));
    }
}
