package coupon.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import coupon.repository.MemberCouponRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;

    @MockBean
    private MemberCouponRepository memberCouponRepository;

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
}
