package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.Dto.CouponOfMemberResponse;
import coupon.Dto.CouponResponse;
import coupon.domain.MemberCoupon;
import coupon.domain.coupon.Coupon;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private MemberCouponRepository memberCouponRepository;
    @Autowired
    private MemberService memberService;

    @BeforeEach
    void setup() {
        memberCouponRepository.deleteAll();
        couponRepository.deleteAll();
    }

    @Test
    @DisplayName("회원의 쿠폰 목록을 조회할 수 있다.")
    void getCouponsOfMember() {
        Coupon coupon1 = new Coupon("쿠폰1", 10000, 1000, "FOOD", LocalDate.of(2024, 12, 20), LocalDate.of(2024, 12, 20));
        Coupon coupon2 = new Coupon("쿠폰2", 20000, 3000, "FASHION", LocalDate.of(2024, 1, 1), LocalDate.of(2025, 1, 1));
        Long couponId1 = couponRepository.save(coupon1).getId();
        Long couponId2 = couponRepository.save(coupon2).getId();

        MemberCoupon memberCoupon1 = MemberCoupon.issue(couponId1, 1L);
        MemberCoupon memberCoupon2 = MemberCoupon.issue(couponId1, 1L);
        MemberCoupon memberCoupon3 = MemberCoupon.issue(couponId2, 1L);
        Long memberCouponId1 = memberCouponRepository.save(memberCoupon1).getId();
        Long memberCouponId2 = memberCouponRepository.save(memberCoupon2).getId();
        Long memberCouponId3 = memberCouponRepository.save(memberCoupon3).getId();

        List<CouponOfMemberResponse> expected = List.of(
                new CouponOfMemberResponse(memberCouponId1, false, LocalDate.now(), LocalDate.now().plusDays(6),
                        new CouponResponse(couponId1, "쿠폰1", 10000, 1000, "FOOD", LocalDate.of(2024, 12, 20),
                                LocalDate.of(2024, 12, 20))),
                new CouponOfMemberResponse(memberCouponId2, false, LocalDate.now(), LocalDate.now().plusDays(6),
                        new CouponResponse(couponId1, "쿠폰1", 10000, 1000, "FOOD", LocalDate.of(2024, 12, 20),
                                LocalDate.of(2024, 12, 20))),
                new CouponOfMemberResponse(memberCouponId3, false, LocalDate.now(), LocalDate.now().plusDays(6),
                        new CouponResponse(couponId2, "쿠폰2", 20000, 3000, "FASHION", LocalDate.of(2024, 1, 1),
                                LocalDate.of(2025, 1, 1)))
        );

        assertThat(memberService.getCouponsOf(1L)).containsExactlyInAnyOrderElementsOf(expected);
    }
}
