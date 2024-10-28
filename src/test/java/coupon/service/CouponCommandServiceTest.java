package coupon.service;

import static coupon.domain.Category.FOOD;
import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.repository.MemberRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CouponCommandServiceTest {


    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponCommandService couponCommandService;

    @Autowired
    private MemberCouponRepository memberCouponRepository;


    @DisplayName("쿠폰을 발급 할 수 있다.")
    @Transactional
    @Test
    void issueCoupon() {
        Member savedMember = memberRepository.save(new Member());
        Coupon savedCoupon = couponRepository.save(
                new Coupon("쿠폰1", FOOD, 1_000, 10_000, LocalDateTime.now(), LocalDateTime.now().plusDays(1))
        );

        couponCommandService.issueCoupon(savedMember.getId(), savedCoupon.getId());

        assertThat(memberCouponRepository.count()).isOne();
    }
}
