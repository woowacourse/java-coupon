package coupon.service;

import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;
import coupon.coupon.dto.MemberCouponRequest;
import coupon.coupon.entity.CouponEntity;
import coupon.coupon.entity.MemberCouponEntity;
import coupon.coupon.repository.CouponRepository;
import coupon.coupon.repository.MemberCouponRepository;
import coupon.coupon.service.MemberCouponService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

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

    @DisplayName("회원이 쿠폰을 5개까지 발급할 수 있다")
    @Test
    void createMemberCouponFive() {
        Coupon coupon = new Coupon(
                "테스트 쿠폰",
                List.of(),
                1000,
                10000,
                Category.ELECTRONICS,
                LocalDate.of(2024, 10, 24),
                LocalDate.of(2024, 10, 26)
        );
        CouponEntity couponEntity = couponRepository.save(new CouponEntity(coupon));
        MemberCouponEntity memberCoupon1 = new MemberCouponEntity(couponEntity.getId(), 1L, false, LocalDate.of(2024, 10, 25));
        MemberCouponEntity memberCoupon2 = new MemberCouponEntity(couponEntity.getId(), 1L, false, LocalDate.of(2024, 10, 25));
        MemberCouponEntity memberCoupon3 = new MemberCouponEntity(couponEntity.getId(), 1L, false, LocalDate.of(2024, 10, 25));
        MemberCouponEntity memberCoupon4 = new MemberCouponEntity(couponEntity.getId(), 1L, false, LocalDate.of(2024, 10, 25));
        memberCouponRepository.saveAll(List.of(memberCoupon1, memberCoupon2, memberCoupon3, memberCoupon4));

        MemberCouponRequest memberCouponRequest = new MemberCouponRequest(
                couponEntity.getId(), 1L, LocalDate.of(2024, 10, 25));

        memberCouponService.create(memberCouponRequest);
        assertThat(memberCouponRepository.findByCouponIdAndMemberId(couponEntity.getId(), 1L)).hasSize(5);
    }

    @DisplayName("회원이 쿠폰을 5개 초과로 발급할 수 없다")
    @Test
    void createMemberCouponSix() {
        Coupon coupon = new Coupon(
                "테스트 쿠폰",
                List.of(),
                1000,
                10000,
                Category.ELECTRONICS,
                LocalDate.of(2024, 10, 24),
                LocalDate.of(2024, 10, 26)
        );
        CouponEntity couponEntity = couponRepository.save(new CouponEntity(coupon));
        MemberCouponEntity memberCoupon1 = new MemberCouponEntity(couponEntity.getId(), 1L, false, LocalDate.of(2024, 10, 25));
        MemberCouponEntity memberCoupon2 = new MemberCouponEntity(couponEntity.getId(), 1L, false, LocalDate.of(2024, 10, 25));
        MemberCouponEntity memberCoupon3 = new MemberCouponEntity(couponEntity.getId(), 1L, false, LocalDate.of(2024, 10, 25));
        MemberCouponEntity memberCoupon4 = new MemberCouponEntity(couponEntity.getId(), 1L, false, LocalDate.of(2024, 10, 25));
        MemberCouponEntity memberCoupon5 = new MemberCouponEntity(couponEntity.getId(), 1L, false, LocalDate.of(2024, 10, 25));
        memberCouponRepository.saveAll(List.of(memberCoupon1, memberCoupon2, memberCoupon3, memberCoupon4, memberCoupon5));

        MemberCouponRequest memberCouponRequest = new MemberCouponRequest(
                couponEntity.getId(), 1L, LocalDate.of(2024, 10, 25));

        assertThatThrownBy(() -> memberCouponService.create(memberCouponRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("최대 5장까지만 발급가능합니다");
    }

    @DisplayName("쿠폰 발급 기간 외에는 쿠폰을 발급할 수 없다")
    @ParameterizedTest
    @ValueSource(ints = {21, 31})
    void validateCouponIssuanceOutPeriod(int day) {
        Coupon coupon = new Coupon(
                "테스트 쿠폰",
                List.of(),
                1000,
                10000,
                Category.ELECTRONICS,
                LocalDate.of(2024, 10, 24),
                LocalDate.of(2024, 10, 26)
        );
        CouponEntity couponEntity = couponRepository.save(new CouponEntity(coupon));
        MemberCouponRequest request = new MemberCouponRequest(couponEntity.getId(), 1L, LocalDate.of(2024, 10, day));

        assertThatThrownBy(() -> memberCouponService.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("발급 기간이 유효하지 않습니다");
    }

    @DisplayName("쿠폰 발급 기간에는 쿠폰을 발급할 수 있다")
    @ParameterizedTest
    @ValueSource(ints = {24, 25, 26})
    void validateCouponIssuanceInPeriod(int day) {
        Coupon coupon = new Coupon(
                "테스트 쿠폰",
                List.of(),
                1000,
                10000,
                Category.ELECTRONICS,
                LocalDate.of(2024, 10, 24),
                LocalDate.of(2024, 10, 26)
        );
        CouponEntity couponEntity = couponRepository.save(new CouponEntity(coupon));
        LocalDate issueDate = LocalDate.of(2024, 10, day);
        MemberCouponRequest request = new MemberCouponRequest(couponEntity.getId(), 1L, issueDate);

        memberCouponService.create(request);

        List<MemberCouponEntity> memberCoupons = memberCouponRepository.findByCouponIdAndMemberId(couponEntity.getId(), 1L);
        assertThat(memberCoupons.get(0).getIssuedDate()).isEqualTo(issueDate);
    }

    @DisplayName("맴버 쿠폰 조회시 쿠폰 정보도 함께 조회한다")
    @Test
    void findByMemberId() {
        Coupon coupon = new Coupon(
                "테스트 쿠폰",
                List.of(),
                1000,
                10000,
                Category.ELECTRONICS,
                LocalDate.of(2024, 10, 24),
                LocalDate.of(2024, 10, 26)
        );
        CouponEntity couponEntity = couponRepository.save(new CouponEntity(coupon));
        MemberCouponEntity memberCoupon = new MemberCouponEntity(couponEntity.getId(), 1L, false, LocalDate.of(2024, 10, 25));
        memberCouponRepository.save(memberCoupon);

        MemberCouponEntity memberCouponEntity = memberCouponService.findByMemberId(1L).get(0);

        assertAll(
                () -> assertThat(memberCouponEntity.getMemberId()).isEqualTo(1L),
                () -> assertThat(memberCouponEntity.getCouponId()).isEqualTo(couponEntity.getId())
        );
    }
}
