package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.Coupon;
import coupon.dto.request.SaveCouponRequest;
import coupon.exception.CouponException;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CouponQueryServiceTest {

    @Autowired
    private CouponQueryService couponQueryService;

    @Autowired
    private CouponCommandService couponCommandService;

    @DisplayName("성공: 존재하는 ID로 조회, 복제 지연 해결")
    @Test
    void findById() {
        Coupon saved = couponCommandService.save(new SaveCouponRequest("천원 할인 쿠폰", 1000, 10000,
                LocalDate.now().minusDays(5), LocalDate.now().plusDays(5), "FOOD"));

        Coupon found = couponQueryService.findById(saved.getId());

        assertThat(saved.getId()).isEqualTo(found.getId());
    }

    @DisplayName("실패: 존재하지 않는 ID로 조회")
    @Test
    void findById_Fail() {
        assertThatThrownBy(() -> couponQueryService.findById(-1L))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("성공: 나의 쿠폰 조회")
    @Test
    void findMine() {
//        Member member = memberRepository.save(new Member("teni"));
//        Coupon coupon = couponCommandService.save(new SaveCouponRequest("천원 할인 쿠폰", 1000, 10000,
//                LocalDate.now().minusDays(5), LocalDate.now().plusDays(5), "FOOD"));
//
//        // 5개 저장
//        couponCommandService.issue(member, coupon);
//        couponCommandService.issue(member, coupon);
//        couponCommandService.issue(member, coupon);
//        couponCommandService.issue(member, coupon);
//        couponCommandService.issue(member, coupon);
//
//        assertThat(couponQueryService.findMine(member.getId())).containsExactly(coupon, coupon, coupon, coupon, coupon);
    }
}
