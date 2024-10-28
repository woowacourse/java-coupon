package coupon.service;

import coupon.domain.MemberCouponDetails;
import coupon.entity.CouponEntity;
import coupon.entity.MemberCouponEntity;
import coupon.entity.MemberEntity;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class MemberCouponServiceTest {

    @Autowired
    Set<JdbcTemplate> jdbcTemplates;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    MemberCouponService memberCouponService;

    MemberEntity member;
    CouponEntity coupon;
    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @BeforeEach
    void setUp() throws InterruptedException {
        member = memberRepository.save(new MemberEntity(1L, "카피"));
        coupon = couponRepository.save(new CouponEntity(1000, 10000));
        Thread.sleep(2000);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplates.forEach((jdbcTemplate) -> {
            jdbcTemplate.update("DELETE FROM member_coupon");
            jdbcTemplate.update("DELETE FROM member");
            jdbcTemplate.update("DELETE FROM coupon");
            jdbcTemplate.update("ALTER TABLE member_coupon AUTO_INCREMENT = 1");
            jdbcTemplate.update("ALTER TABLE member AUTO_INCREMENT = 1");
            jdbcTemplate.update("ALTER TABLE coupon AUTO_INCREMENT = 1");
        });
    }

    @DisplayName("멤버가 쿠폰을 발급한다.")
    @Test
    void issue() {
        assertThatCode(() -> memberCouponService.issue(coupon.toCoupon().getId(), member.toMember()))
                .doesNotThrowAnyException();
    }

    @DisplayName("최대 발급 수량을 초과하면 쿠폰을 발급할 수 없다.")
    @Test
    void failToIssue_overMaxIssueCount() {
        for (int i = 0; i < 5; i++) {
            memberCouponService.issue(coupon.toCoupon().getId(), member.toMember());
        }

        assertThatThrownBy(() -> memberCouponService.issue(coupon.toCoupon().getId(), member.toMember()));
    }

    @DisplayName("발급 쿠폰 목록을 조회한다.")
    @Test
    void getMemberCoupons() {
        memberCouponService.issue(coupon.toCoupon().getId(), member.toMember());

        List<MemberCouponDetails> coupons = memberCouponService.getCoupons(member.toMember());

        assertAll(
                () -> assertThat(coupons).hasSize(1),
                () -> assertThat(coupons.get(0).getCoupon()).isNotNull()
        );
    }

    @DisplayName("쿠폰이 존재하지 않아도 다른 목록은 조회 가능하다.")
    @Test
    void getMemberCouponsWithoutAbsentCoupon() {
        memberCouponService.issue(coupon.toCoupon().getId(), member.toMember());
        LocalDateTime now = LocalDateTime.now();
        memberCouponRepository.save(new MemberCouponEntity(2L, 0L, member, false, now, now.plusDays(7)));

        List<MemberCouponDetails> coupons = memberCouponService.getCoupons(member.toMember());

        assertAll(
                () -> assertThat(coupons).hasSize(1),
                () -> assertThat(coupons.get(0).getCoupon()).isNotNull()
        );
    }
}
