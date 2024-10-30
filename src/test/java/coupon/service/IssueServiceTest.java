package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.IssuedCoupon;
import coupon.domain.Member;
import coupon.repository.MemberRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IssueServiceTest {

    @Autowired
    private IssueService issueService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CouponService couponService;

    @Test
    @DisplayName("쿠폰 발급 테스트")
    void issueTest() throws InterruptedException {
        //given
        Coupon coupon = couponService.create(
                new Coupon("name", 1000, 10000, Category.FASHION, LocalDate.now(), LocalDate.now().plusDays(7)));
        Member member = memberService.save(new Member());

        //when
        IssuedCoupon issue = issueService.issue(member.getId(), coupon.getId());

        //then
        assertThat(issue.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("쿠폰 조회 테스트")
    void getIssuedCouponsTest() {
        //given
        Coupon coupon = couponService.create(
                new Coupon("name", 1000, 10000, Category.FASHION, LocalDate.now(), LocalDate.now().plusDays(7)));
        Member member = memberService.save(new Member());

        //when
        issueService.issue(member.getId(), coupon.getId());
        issueService.issue(member.getId(), coupon.getId());
        issueService.issue(member.getId(), coupon.getId());
        issueService.issue(member.getId(), coupon.getId());
        issueService.issue(member.getId(), coupon.getId());

        //then
        assertThat(issueService.getIssuedCouponBy(member.getId()))
                .hasSize(5)
                .allMatch(issuedCouponResponse -> issuedCouponResponse.couponResponse().id() == coupon.getId());
    }
}
