package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import coupon.domain.coupon.Coupon;
import coupon.domain.member.Member;
import coupon.domain.payment.Category;
import coupon.domain.payment.Payment;
import coupon.domain.repository.CouponRepository;
import coupon.domain.repository.InMemoryCouponRepository;
import coupon.domain.repository.MemberRepository;
import coupon.domain.repository.PaymentRepository;
import coupon.support.Dummy;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class CouponIssueServiceTest {

    @Autowired
    private CouponIssueService couponIssueService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @AfterEach
    void tearDown() {
        couponRepository.deleteAll();
        paymentRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자에게 쿠폰을 인메모리 캐시에 저장한다.")
    void issue_member_coupon_into_in_memory_database() {
        // given
        final Payment payment = paymentRepository.save(Dummy.createPayment(10000L, Category.FASHION));
        final Member member = memberRepository.save(Dummy.createMember("hello"));
        final Coupon coupon = couponRepository.save(Dummy.createCoupon("coupon", payment));

        // when
        couponIssueService.issueNewCoupon(member.getId(), coupon.getId());

        // then
        assertThat(InMemoryCouponRepository.getByMemberId(member.getId()))
                .hasSize(1);
    }

    @Test
    @DisplayName("캐시에 저장된 회원 쿠폰 정보를 불러온다.")
    void get_issued_coupon_specific() {
        // given
        final Payment payment = paymentRepository.save(Dummy.createPayment(10000L, Category.FASHION));
        final Member member = memberRepository.save(Dummy.createMember("fram"));
        final Coupon coupon = couponRepository.save(Dummy.createCoupon("coupon", payment));

        couponIssueService.issueNewCoupon(member.getId(), coupon.getId());
        couponIssueService.issueNewCoupon(member.getId(), coupon.getId());
        couponIssueService.issueNewCoupon(member.getId(), coupon.getId());

        // when
        final List<Coupon> allIssuedCoupon = couponIssueService.findAllIssuedCoupon(member.getId());

        // then
        assertThat(allIssuedCoupon).hasSize(3);
    }
}
