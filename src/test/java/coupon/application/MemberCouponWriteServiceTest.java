package coupon.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponName;
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
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class MemberCouponWriteServiceTest {

    @Autowired
    private MemberCouponWriteService memberCouponWriteService;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        memberCouponRepository.deleteAll();
        memberRepository.deleteAll();
        entityManager.createNativeQuery("ALTER TABLE MemberCoupon AUTO_INCREMENT = 1")
                .executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE Member AUTO_INCREMENT = 1").executeUpdate();
    }

    @Test
    @DisplayName("멤버 쿠폰을 생성한다.")
    void create() {
        Member member = new Member(1L);
        memberRepository.save(member);
        Coupon coupon = new Coupon(
                1L,
                new CouponName("쿠폰"),
                new DiscountAmount(new BigDecimal(1_000)),
                new MinimumOrderAmount(new BigDecimal(30_000)),
                Category.FOOD,
                new IssuancePeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        MemberCoupon memberCoupon = new MemberCoupon(
                1L,
                coupon.getId(),
                member,
                false,
                LocalDateTime.now().minusDays(1)
        );

        MemberCoupon savedMemberCoupon = memberCouponWriteService.create(memberCoupon);

        assertThat(savedMemberCoupon.getId()).isEqualTo(memberCoupon.getId());
    }

    @Test
    @DisplayName("한 명의 회원이 동일한 쿠폰을 5회 초과로 발급하는 경우 예외가 발생한다.")
    void validateCreateCount() {
        Member member = new Member(1L);
        memberRepository.save(member);
        Coupon coupon = new Coupon(
                1L,
                new CouponName("쿠폰"),
                new DiscountAmount(new BigDecimal(1_000)),
                new MinimumOrderAmount(new BigDecimal(30_000)),
                Category.FOOD,
                new IssuancePeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
        for (int i = 0; i < 6; i++) {
            MemberCoupon memberCoupon = new MemberCoupon(
                    null,
                    coupon.getId(),
                    member,
                    false,
                    LocalDateTime.now().minusDays(1)
            );
            memberCouponWriteService.create(memberCoupon);
        }

        assertThatThrownBy(() -> {
            MemberCoupon memberCoupon = new MemberCoupon(
                    null,
                    coupon.getId(),
                    member,
                    false,
                    LocalDateTime.now().minusDays(1)
            );
            memberCouponWriteService.create(memberCoupon);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
