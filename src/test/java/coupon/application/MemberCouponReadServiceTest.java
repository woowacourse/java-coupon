package coupon.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponName;
import coupon.domain.coupon.CouponRepository;
import coupon.domain.coupon.DiscountAmount;
import coupon.domain.coupon.IssuancePeriod;
import coupon.domain.coupon.MinimumOrderAmount;
import coupon.domain.member.Member;
import coupon.domain.member.MemberRepository;
import coupon.domain.membercoupon.MemberCoupon;
import coupon.domain.membercoupon.MemberCouponRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class MemberCouponReadServiceTest {

    @Autowired
    private MemberCouponReadService memberCouponReadService;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        memberCouponRepository.deleteAll();
        memberRepository.deleteAll();
        couponRepository.deleteAll();
        entityManager.createNativeQuery("ALTER TABLE MemberCoupon AUTO_INCREMENT = 1")
                .executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE Member AUTO_INCREMENT = 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE Coupon AUTO_INCREMENT = 1").executeUpdate();
    }

    @Test
    @DisplayName("회원 식별자로 회원의 쿠폰 목록을 조회한다.")
    void getMemberCoupons() {
        Member member = new Member(1L);
        memberRepository.save(member);
        Coupon coupon = new Coupon(
                1L,
                new CouponName("쿠폰"),
                new DiscountAmount(new BigDecimal(1_000)),
                new MinimumOrderAmount(new BigDecimal(30_000)),
                Category.FOOD,
                new IssuancePeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        couponRepository.save(coupon);

        for (int i = 0; i < 3; i++) {
            MemberCoupon memberCoupon = new MemberCoupon(
                    null,
                    coupon.getId(),
                    member,
                    false,
                    LocalDateTime.now().minusDays(1)
            );
            memberCouponRepository.save(memberCoupon);
        }

        assertThat(memberCouponReadService.getMemberCoupons(member.getId())).hasSize(3);
    }
}
