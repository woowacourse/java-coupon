package coupon.coupon.business;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.coupon.common.Fixture;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.IssuePeriod;
import coupon.coupon.domain.MemberCoupon;
import coupon.coupon.dto.MemberCouponResponse;
import coupon.coupon.persistence.CouponWriter;
import coupon.member.domain.Member;
import coupon.member.persistence.MemberWriter;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private MemberWriter memberWriter;

    @Autowired
    private CouponWriter couponWriter;

    @Autowired
    private CacheManager cacheManager;

    @DisplayName("회원에게 쿠폰을 발급 시 발행 가능한 최대 개수를 초과하면 예외가 발생한다.")
    @Test
    void issueCouponFailByIssueCount() {
        Member member = memberWriter.create(Fixture.generateMember());
        IssuePeriod issuePeriod = new IssuePeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1L));
        Coupon coupon = couponWriter.create(Fixture.generateBigSaleFashionCoupon(issuePeriod));

        memberCouponService.issueCoupon(member.getId(), coupon.getId());
        memberCouponService.issueCoupon(member.getId(), coupon.getId());
        memberCouponService.issueCoupon(member.getId(), coupon.getId());
        memberCouponService.issueCoupon(member.getId(), coupon.getId());
        memberCouponService.issueCoupon(member.getId(), coupon.getId());

        assertThatThrownBy(() -> memberCouponService.issueCoupon(member.getId(), coupon.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("회원에게 쿠폰을 발급 시 발행 가능한 기간이 아닌 쿠폰일 경우 예외가 발생한다.")
    @Test
    void issueCouponFailByNotUsableCoupon() {
        Member member = memberWriter.create(Fixture.generateMember());
        IssuePeriod issuePeriod = new IssuePeriod(LocalDateTime.now().plusDays(1L), LocalDateTime.now().plusDays(2L));

        Coupon coupon = couponWriter.create(Fixture.generateBigSaleFashionCoupon(issuePeriod));

        assertThatThrownBy(() -> memberCouponService.issueCoupon(member.getId(), coupon.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("회원에게 쿠폰을 발급한다.")
    @Test
    void issueCoupon() {
        Member member = memberWriter.create(Fixture.generateMember());
        IssuePeriod issuePeriod = new IssuePeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1L));

        Coupon coupon = couponWriter.create(Fixture.generateBigSaleFashionCoupon(issuePeriod));

        assertThatCode(() -> memberCouponService.issueCoupon(member.getId(), coupon.getId()))
                .doesNotThrowAnyException();
    }

    @DisplayName("쿠폰 발행시 특정 캐시 데이터를 제거한다.")
    @Test
    void deleteCacheWhenIssueCoupon() {
        Member member = memberWriter.create(Fixture.generateMember());
        IssuePeriod issuePeriod = new IssuePeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1L));
        Coupon coupon = couponWriter.create(Fixture.generateBigSaleFashionCoupon(issuePeriod));

        Cache cache = cacheManager.getCache(MemberCouponService.MEMBER_COUPON_CACHE_NAME);
        List<MemberCouponResponse> memberCouponResponses = List.of(
                MemberCouponResponse.of(new MemberCoupon(member.getId(), coupon.getId()), coupon)
        );
        cache.put(member.getId(), memberCouponResponses);

        memberCouponService.issueCoupon(member.getId(), coupon.getId());

        assertThat(cache.get(member.getId())).isNull();
    }

    @DisplayName("회원의 쿠폰 목록을 조회할 때 캐시 데이터가 없다면 캐시에 저장하고 다음 조회시 캐시에서 반환한다.")
    @Test
    void findAllByMemberIdFromCache() {
        Member member = memberWriter.create(Fixture.generateMember());
        IssuePeriod issuePeriod = new IssuePeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1L));
        Coupon coupon = couponWriter.create(Fixture.generateBigSaleFashionCoupon(issuePeriod));
        memberCouponService.issueCoupon(member.getId(), coupon.getId());

        Cache cache = cacheManager.getCache(MemberCouponService.MEMBER_COUPON_CACHE_NAME);
        assertThat(cache.get(member.getId())).isNull();

        List<MemberCouponResponse> memberCouponResponses = memberCouponService.findAllByMemberId(member.getId());
        List<MemberCouponResponse> cachedData = (List<MemberCouponResponse>) cache.get(member.getId()).get();

        assertThat(memberCouponResponses.get(0).couponId()).isEqualTo(cachedData.get(0).couponId());
    }
}
