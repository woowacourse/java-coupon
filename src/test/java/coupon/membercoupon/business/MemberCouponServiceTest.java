package coupon.membercoupon.business;

import coupon.coupon.domain.Coupon;
import coupon.coupon.exception.CouponException;
import coupon.coupon.infrastructure.CouponRepository;
import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.dto.MemberCouponResponse;
import coupon.membercoupon.fixture.CouponFixture;
import coupon.membercoupon.fixture.MemberCouponFixture;
import coupon.membercoupon.infrastructure.MemberCouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        memberCouponRepository.deleteAll();
        couponRepository.deleteAll();
        jdbcTemplate.execute("ALTER TABLE coupon AUTO_INCREMENT = 1;");
        jdbcTemplate.execute("ALTER TABLE memberCoupon AUTO_INCREMENT = 1;");
    }

    @DisplayName("회원 쿠폰 발급에 성공한다.")
    @Test
    void issueSuccess() {
        // given
        memberCouponRepository.save(MemberCouponFixture.getMemberCoupon());
        memberCouponRepository.save(MemberCouponFixture.getMemberCoupon());
        memberCouponRepository.save(MemberCouponFixture.getMemberCoupon());
        memberCouponRepository.save(MemberCouponFixture.getMemberCoupon());

        // when
        memberCouponService.issue(1L, 1L);

        // then
        List<MemberCoupon> memberCoupons = memberCouponRepository.findByCouponIdAndMemberId(1L, 1L);
        assertThat(memberCoupons).hasSize(5);
    }

    @DisplayName("회원 쿠폰 발급 횟수가 초과되어 발급에 실패한다.")
    @Test
    void issueFail() {
        // given
        memberCouponRepository.save(MemberCouponFixture.getMemberCoupon());
        memberCouponRepository.save(MemberCouponFixture.getMemberCoupon());
        memberCouponRepository.save(MemberCouponFixture.getMemberCoupon());
        memberCouponRepository.save(MemberCouponFixture.getMemberCoupon());
        memberCouponRepository.save(MemberCouponFixture.getMemberCoupon());

        // when & then
        assertThatThrownBy(() -> memberCouponService.issue(1L, 1L))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("회원 쿠폰 조회 성공한다.")
    @Test
    void findAllMemberCouponsSuccess() {
        // given
        Coupon coupon = CouponFixture.getCoupon();
        couponRepository.save(coupon);
        MemberCoupon memberCoupon = MemberCouponFixture.getMemberCoupon();
        memberCouponRepository.save(memberCoupon);

        // when
        List<MemberCouponResponse> responses = memberCouponService.findAllMemberCoupons(1L);

        // then
        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).memberCoupon()).isEqualTo(memberCoupon);
        assertThat(responses.get(0).coupon()).isEqualTo(coupon);
    }

    @DisplayName("존재하지 않는 쿠폰을 참조하는 회원 쿠폰이 있는 경우 조회에 실패한다.")
    @Test
    void findAllMemberCouponsFail() {
        // given
        memberCouponRepository.save(MemberCouponFixture.getMemberCoupon());

        // when & then
        assertThatThrownBy(() -> memberCouponService.findAllMemberCoupons(1L))
                .isInstanceOf(CouponException.class);
    }
}
