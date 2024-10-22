package coupon.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.Coupon;
import coupon.domain.CouponRepository;
import coupon.domain.MemberCoupon;
import coupon.domain.MemberCouponRepository;
import coupon.dto.MemberCouponRequest;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberCouponServiceTest {

    @Autowired
    MemberCouponService memberCouponService;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private CouponRepository couponRepository;

    @BeforeEach
    void setUp() {
        memberCouponRepository.deleteAll();
        couponRepository.deleteAll();
    }

    @DisplayName("회원이 쿠폰을 5개까지 발급할 수 있다.")
    @Test
    void createMemberCouponFive() {
        Coupon coupon = couponRepository.save(
                new Coupon(
                        "쿠폰",
                        5_000,
                        30_000,
                        "가구",
                        LocalDate.of(2024, 10, 22),
                        LocalDate.of(2024, 10, 30)
                )
        );
        MemberCoupon memberCoupon1 = new MemberCoupon(coupon.getId(), 1L, LocalDate.of(2024, 10, 25));
        MemberCoupon memberCoupon2 = new MemberCoupon(coupon.getId(), 1L, LocalDate.of(2024, 10, 25));
        MemberCoupon memberCoupon3 = new MemberCoupon(coupon.getId(), 1L, LocalDate.of(2024, 10, 25));
        MemberCoupon memberCoupon4 = new MemberCoupon(coupon.getId(), 1L, LocalDate.of(2024, 10, 25));
        memberCouponRepository.saveAll(List.of(memberCoupon1, memberCoupon2, memberCoupon3, memberCoupon4));

        MemberCouponRequest memberCouponRequest = new MemberCouponRequest(
                coupon.getId(), 1L, LocalDate.of(2024, 10, 25));

        memberCouponService.create(memberCouponRequest);
        assertThat(memberCouponRepository.findByCouponIdAndMemberId(coupon.getId(), 1L)).hasSize(5);
    }

    @DisplayName("회원이 쿠폰을 5개 초과로 발급할 수 없다.")
    @Test
    void createMemberCouponSix() {
        Coupon coupon = couponRepository.save(
                new Coupon(
                        "쿠폰",
                        5_000,
                        30_000,
                        "가구",
                        LocalDate.of(2024, 10, 22),
                        LocalDate.of(2024, 10, 30)
                )
        );
        MemberCoupon memberCoupon1 = new MemberCoupon(coupon.getId(), 1L, LocalDate.of(2024, 10, 25));
        MemberCoupon memberCoupon2 = new MemberCoupon(coupon.getId(), 1L, LocalDate.of(2024, 10, 25));
        MemberCoupon memberCoupon3 = new MemberCoupon(coupon.getId(), 1L, LocalDate.of(2024, 10, 25));
        MemberCoupon memberCoupon4 = new MemberCoupon(coupon.getId(), 1L, LocalDate.of(2024, 10, 25));
        MemberCoupon memberCoupon5 = new MemberCoupon(coupon.getId(), 1L, LocalDate.of(2024, 10, 25));
        memberCouponRepository.saveAll(List.of(memberCoupon1, memberCoupon2, memberCoupon3, memberCoupon4, memberCoupon5));

        MemberCouponRequest memberCouponRequest = new MemberCouponRequest(
                coupon.getId(), 1L, LocalDate.of(2024, 10, 25));

        assertThatThrownBy(() -> memberCouponService.create(memberCouponRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Too many coupons");
    }

    @DisplayName("쿠폰 발급 기간 외에는 쿠폰을 발급할 수 없다.")
    @ParameterizedTest
    @ValueSource(ints = {21, 31})
    void validateCouponIssuanceOutPeriod(int day) {
        Coupon coupon = couponRepository.save(
                new Coupon(
                        "쿠폰",
                        5_000,
                        30_000,
                        "가구",
                        LocalDate.of(2024, 10, 22),
                        LocalDate.of(2024, 10, 30)
                )
        );
        MemberCouponRequest request = new MemberCouponRequest(coupon.getId(), 1L, LocalDate.of(2024, 10, day));

        assertThatThrownBy(() -> memberCouponService.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid issuance period");
    }

    @DisplayName("쿠폰 발급 기간에는 쿠폰을 발급할 수 있다.")
    @ParameterizedTest
    @ValueSource(ints = {22, 23, 24, 25, 29, 30})
    void validateCouponIssuanceInPeriod(int day) {
        Coupon coupon = couponRepository.save(
                new Coupon(
                        "쿠폰",
                        5_000,
                        30_000,
                        "가구",
                        LocalDate.of(2024, 10, 22),
                        LocalDate.of(2024, 10, 30)
                )
        );
        LocalDate issueDate = LocalDate.of(2024, 10, day);
        MemberCouponRequest request = new MemberCouponRequest(coupon.getId(), 1L, issueDate);

        memberCouponService.create(request);

        List<MemberCoupon> memberCoupons = memberCouponRepository.findByCouponIdAndMemberId(coupon.getId(), 1L);
        assertThat(memberCoupons.get(0).getIssueDate()).isEqualTo(issueDate);
    }
}
