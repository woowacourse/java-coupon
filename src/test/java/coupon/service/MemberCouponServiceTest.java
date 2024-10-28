package coupon.service;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.MemberCoupon;
import coupon.dto.MemberCouponResponse;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;
    @Autowired
    private MemberCouponRepository memberCouponRepository;
    @Autowired
    private CouponRepository couponRepository;


    @Transactional
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

    @Transactional
    @DisplayName("회원의 모든 쿠폰 목록를 반환")
    @Test
    void findByMemberId() {
        //given
        Coupon coupon1 = couponRepository.save(new Coupon("쿠폰1", 5000, 1000,
                Category.FOOD, LocalDateTime.now(), LocalDateTime.now().plusDays(3)));
        Coupon coupon2 = couponRepository.save(new Coupon("쿠폰2", 5000, 1000,
                Category.FOOD, LocalDateTime.now(), LocalDateTime.now().plusDays(3)));

        memberCouponRepository.save(new MemberCoupon(coupon1.getId(), 1L));
        memberCouponRepository.save(new MemberCoupon(coupon1.getId(), 1L));
        memberCouponRepository.save(new MemberCoupon(coupon2.getId(), 1L));
        memberCouponRepository.save(new MemberCoupon(coupon2.getId(), 1L));
        memberCouponRepository.save(new MemberCoupon(coupon2.getId(), 1L));

        //when
        List<MemberCouponResponse> memberCoupons = memberCouponService.findByMemberId(1L);

        //then
        assertThat(memberCoupons.size()).isEqualTo(5);
    }
}
