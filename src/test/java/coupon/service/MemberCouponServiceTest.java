package coupon.service;

import coupon.domain.MemberCoupon;
import coupon.repository.MemberCouponRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;
    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @DisplayName("한명의 회원이 동일한 쿠폰을 이미 5장을 발급받았다면 예외")
    @Test
    void issue_maxCount() {
        //given
        memberCouponRepository.save(new MemberCoupon(1L, 1L));
        memberCouponRepository.save(new MemberCoupon(1L, 1L));
        memberCouponRepository.save(new MemberCoupon(1L, 1L));
        memberCouponRepository.save(new MemberCoupon(1L, 1L));
        memberCouponRepository.save(new MemberCoupon(1L, 1L));

        //when & then
        assertThatThrownBy(() -> memberCouponService.issue(1L, 1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("쿠폰 발급은 최대 5개까지만 가능합니다.");
    }
}
