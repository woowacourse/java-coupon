package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.dto.request.SaveCouponRequest;
import coupon.dto.response.FindMyCouponsResponse;
import coupon.exception.CouponException;
import coupon.repository.CouponRepository;
import coupon.repository.MemberRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CouponCommandServiceTest {

    @Autowired
    private CouponCommandService couponCommandService;

    @Autowired
    private CouponQueryService couponQueryService;

    @Autowired
    private MemberCouponQueryService memberCouponQueryService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponRepository couponRepository;

    @DisplayName("성공: 쿠폰을 저장한다.")
    @Test
    void save() {
        Coupon coupon = couponCommandService.save(new SaveCouponRequest(
                "천원 할인 쿠폰",
                1000,
                10000,
                LocalDate.now().minusDays(10),
                LocalDate.now().plusDays(10),
                "FOOD"));

        Coupon foundCoupon = couponQueryService.findById(coupon.getId());

        assertThat(foundCoupon.getName()).isEqualTo("천원 할인 쿠폰");
    }

    @DisplayName("성공: 쿠폰을 발급한다.")
    @Test
    void issue() {
        Member member = memberRepository.save(new Member("트레"));
        Coupon coupon = couponCommandService.save(new SaveCouponRequest("천원 할인",
                1000, 10000,
                LocalDate.now().minusDays(2), LocalDate.now().plusDays(2),
                "FASHION"));

        couponCommandService.issue(member, coupon);
        assertThat(memberCouponQueryService.findByMemberId(member.getId()))
                .extracting(FindMyCouponsResponse::couponId)
                .containsExactly(coupon.getId());
    }

    @DisplayName("실패: 동일한 쿠폰 6장 이상 발급 불가 (사용 여부 관련 X)")
    @Test
    void issue_Fail() {
        Member member = memberRepository.save(new Member("트레"));
        Coupon coupon = couponRepository.save(new Coupon("천원 할인",
                1000, 10000,
                LocalDate.now().minusDays(2), LocalDate.now().plusDays(2),
                "FASHION"));

        couponCommandService.issue(member, coupon);
        couponCommandService.issue(member, coupon);
        couponCommandService.issue(member, coupon);
        couponCommandService.issue(member, coupon);
        couponCommandService.issue(member, coupon);

        assertThatThrownBy(() -> couponCommandService.issue(member, coupon))
                .isInstanceOf(CouponException.class)
                .hasMessage("동일한 쿠폰은 최대 5장까지 발행할 수 있습니다.");
    }
}
