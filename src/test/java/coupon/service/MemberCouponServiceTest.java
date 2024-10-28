package coupon.service;

import coupon.domain.MemberCoupon;
import coupon.entity.CouponEntity;
import coupon.entity.MemberEntity;
import coupon.repository.CouponRepository;
import coupon.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @DisplayName("발급 쿠폰을 조회한다.")
    @Test
    void getMemberCoupons() {
        memberCouponService.issue(coupon.toCoupon().getId(), member.toMember());

        List<MemberCoupon> memberCoupons = memberCouponService.getCoupons(member.toMember());

        assertThat(memberCoupons).hasSize(1);
    }
}
