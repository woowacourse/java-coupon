package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.dto.request.SaveCouponRequest;
import coupon.dto.response.FindMyCouponsResponse;
import coupon.repository.MemberRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberCouponQueryServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponCommandService couponCommandService;

    @Autowired
    private MemberCouponQueryService memberCouponQueryService;

    @DisplayName("")
    @Test
    void findByMemberId() {
        Member member = memberRepository.save(new Member("teni"));
        Coupon coupon = couponCommandService.save(new SaveCouponRequest("천원 할인 쿠폰", 1000, 10000,
                LocalDate.now().minusDays(5), LocalDate.now().plusDays(5), "FOOD"));

        // 5개 저장
        couponCommandService.issue(member, coupon);
        couponCommandService.issue(member, coupon);
        couponCommandService.issue(member, coupon);
        couponCommandService.issue(member, coupon);
        couponCommandService.issue(member, coupon);

        List<FindMyCouponsResponse> response = memberCouponQueryService.findByMemberId(member.getId());

        assertThat(response).extracting(FindMyCouponsResponse::couponId)
                .hasSize(5)
                .allMatch(savedCouponId -> Objects.equals(savedCouponId, coupon.getId()));
    }
}
