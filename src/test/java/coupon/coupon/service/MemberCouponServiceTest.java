package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.MemberCoupon;
import coupon.coupon.domain.repository.CouponRepository;
import coupon.coupon.domain.repository.MemberCouponRepository;
import coupon.coupon.dto.MemberCouponResponse;
import coupon.coupon.fixture.CouponFixture;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = "/reset-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @AfterEach
    void clearRedisCache() {
        List<String> patterns = List.of("*coupon*");
        patterns.forEach(pattern ->
                redisTemplate.keys(pattern).forEach(redisTemplate::delete)
        );
    }

    @DisplayName("회원에게 쿠폰을 발급한다.")
    @Test
    void issueCoupon() {
        // given
        long memberId = 1L;
        Coupon savedCoupon = couponRepository.save(CouponFixture.createFoodCoupon());

        // when
        MemberCoupon actual = memberCouponService.issueCoupon(memberId, savedCoupon.getId());

        // then
        assertThat(actual.getMemberId()).isEqualTo(memberId);
        assertThat(actual.getCoupon()).isEqualTo(savedCoupon);
    }

    @DisplayName("1인당 쿠폰 발급 제한을 초과한 경우 예외가 발생한다.")
    @Test
    void throwsException_whenExceedsMaxCouponCountPerMember() {
        // given
        long memberId = 1L;
        Coupon coupon = couponRepository.save(CouponFixture.createFoodCoupon());
        int maxCouponCountPerMember = 5;
        while (maxCouponCountPerMember-- > 0) {
            memberCouponRepository.save(new MemberCoupon(coupon, memberId));
        }

        // when & then
        assertThatThrownBy(() -> memberCouponService.issueCoupon(memberId, coupon.getId()))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("회원의 쿠폰 목록을 조회한다.")
    @Test
    void getMemberCoupons() {
        // given
        long memberId = 1L;
        Coupon foodCoupon = couponRepository.save(CouponFixture.createFoodCoupon());
        Coupon furnitureCoupon = couponRepository.save(CouponFixture.createFurnitureCoupon());
        memberCouponService.issueCoupon(memberId, foodCoupon.getId());
        memberCouponService.issueCoupon(memberId, furnitureCoupon.getId());

        // when
        List<MemberCouponResponse> memberCoupons = memberCouponService.getMemberCoupons(memberId);

        // then
        assertThat(memberCoupons).hasSize(2);
        assertThat(memberCoupons.get(0).memberCouponId()).isEqualTo(foodCoupon.getId());
        assertThat(memberCoupons.get(1).memberCouponId()).isEqualTo(furnitureCoupon.getId());
    }
}
