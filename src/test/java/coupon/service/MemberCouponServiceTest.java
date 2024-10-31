package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.IssuancePeriod;
import coupon.domain.membercoupon.MemberCoupon;
import coupon.dto.response.FindMemberCouponResponse;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.support.CouponFixtureGenerator;
import coupon.support.MemberCouponFixtureGenerator;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;

    @MockBean
    private MemberCouponRepository memberCouponRepository;

    @MockBean
    private CouponRepository couponRepository;

    @MockBean
    private ReadCouponService readCouponService;

    @DisplayName("한 명의 회원은 동일한 쿠폰을 사용한 쿠폰을 포함하여 최대 5장까지 발급할 수 있다.")
    @Test
    void validateIssuanceLimit() {
        long couponId = 1L;
        long memberId = 1L;
        when(memberCouponRepository.countByCouponIdAndMemberId(couponId, memberId)).thenReturn(5);

        assertAll(
                () -> assertThatThrownBy(() -> memberCouponService.save(couponId, memberId))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> verify(memberCouponRepository, never()).save(any())
        );
    }

    @DisplayName("해당 쿠폰은 시작일부터 종료일까지 발급할 수 있다.")
    @ParameterizedTest
    @ValueSource(ints = {1, -1})
    void validateIssuancePeriod(int days) {
        long couponId = 1L;
        long memberId = 1L;

        LocalDateTime time = LocalDateTime.now().plusDays(days);
        IssuancePeriod issuancePeriod = mock(IssuancePeriod.class);
        when(issuancePeriod.getStartAt()).thenReturn(time);
        when(issuancePeriod.getEndAt()).thenReturn(time);

        Coupon coupon = mock(Coupon.class);
        when(coupon.getIssuancePeriod()).thenReturn(issuancePeriod);
        when(readCouponService.findById(couponId)).thenReturn(coupon);

        assertAll(
                () -> assertThatThrownBy(() -> memberCouponService.save(couponId, memberId))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> verify(memberCouponRepository, never()).save(any())
        );
    }

    @DisplayName("회원의 쿠폰 목록 조회 시 쿠폰, 회원에게 발급된 쿠폰의 정보를 모두 보여줄 수 있어야 한다.")
    @Test
    void findByMemberId() {
        Coupon coupon = CouponFixtureGenerator.generate();

        long memberId = 1L;
        MemberCoupon memberCoupon = MemberCouponFixtureGenerator.generate(coupon.getId(), memberId);
        when(memberCouponRepository.findAllByMemberId(memberId)).thenReturn(List.of(memberCoupon));
        when(couponRepository.findById(coupon.getId())).thenReturn(Optional.of(coupon));
        when(readCouponService.findById(memberCoupon.getCouponId())).thenReturn(coupon);

        FindMemberCouponResponse actual = memberCouponService.findByMemberId(memberId).get(0);

        assertAll(
                () -> assertThat(actual.memberCouponId()).isEqualTo(memberCoupon.getId()),
                () -> assertThat(actual.couponId()).isEqualTo(memberCoupon.getCouponId()),
                () -> assertThat(actual.memberId()).isEqualTo(memberCoupon.getMemberId()),
                () -> assertThat(actual.used()).isEqualTo(memberCoupon.isUsed()),
                () -> assertThat(actual.issuedAt()).isEqualTo(memberCoupon.getIssuedAt()),
                () -> assertThat(actual.expiresAt()).isEqualTo(memberCoupon.getExpiresAt()),

                () -> assertThat(actual.name()).isEqualTo(coupon.getName().getValue()),
                () -> assertThat(actual.discountAmount()).isEqualTo(coupon.getDiscountAmount().getValue()),
                () -> assertThat(actual.minOrderAmount()).isEqualTo(coupon.getMinOrderAmount().getValue()),
                () -> assertThat(actual.category()).isEqualTo(coupon.getCategory().name()),
                () -> assertThat(actual.startAt()).isEqualTo(coupon.getIssuancePeriod().getStartAt()),
                () -> assertThat(actual.endAt()).isEqualTo(coupon.getIssuancePeriod().getEndAt())
        );
    }
}
