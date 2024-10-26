package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import coupon.domain.Category;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponName;
import coupon.domain.coupon.DiscountAmount;
import coupon.domain.coupon.IssueDuration;
import coupon.domain.coupon.MemberCoupon;
import coupon.domain.coupon.MinimumOrderAmount;
import coupon.dto.response.GetMemberCouponResponse;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberCouponQueryServiceTest {

    @Autowired
    private MemberCouponQueryService memberCouponQueryService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    private Coupon savedCoupon;
    private long memberId = 1;

    @BeforeEach
    void setup() {
        Coupon coupon = new Coupon(
                new CouponName("쿠폰1"),
                new DiscountAmount("1000"),
                new MinimumOrderAmount("33333"),
                Category.ELECTRONICS,
                new IssueDuration(
                        LocalDateTime.of(2024, 1, 1, 1, 0, 0),
                        LocalDateTime.of(2025, 1, 1, 1, 0, 1)
                )
        );
        savedCoupon = couponRepository.save(coupon);

        memberCouponRepository.save(new MemberCoupon(memberId, coupon.getId(), LocalDateTime.of(2024, 2, 1, 1, 0, 0)));
        memberCouponRepository.save(new MemberCoupon(memberId, coupon.getId(), LocalDateTime.of(2024, 4, 1, 1, 0, 0)));
    }

    @DisplayName("회원의 쿠폰 목록 조회 시 쿠폰, 회원에게 발급된 쿠폰의 정보를 반환한다.")
    @Test
    void getMemberCoupons() {
        List<GetMemberCouponResponse> actual = memberCouponQueryService.getMemberCoupons(memberId);

        assertAll(
                () -> assertThat(actual).extracting("memberCouponId").containsExactly(1L, 2L),
                () -> assertThat(actual).extracting("couponId").containsExactly(1L, 1L),
                () -> assertThat(actual).extracting("couponName")
                        .containsExactly(savedCoupon.getCouponName().getValue(),
                                savedCoupon.getCouponName().getValue()),
                () -> assertThat(actual).extracting("discountRate")
                        .containsExactly(savedCoupon.getDiscountRate().getValue(),
                                savedCoupon.getDiscountRate().getValue())
        );
    }
}
