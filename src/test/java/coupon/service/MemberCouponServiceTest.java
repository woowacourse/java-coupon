package coupon.service;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.dto.MemberCouponInfo;
import coupon.repository.CouponRepository;
import coupon.repository.MemberRepository;
import coupon.support.Fixture;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

@SpringBootTest
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private MemberRepository memberRepository;

    @SpyBean
    private CouponRepository couponRepository;

    @SpyBean
    private CacheManager cacheManager;

    @Nested
    class 회원_쿠폰_발급 {

        @Test
        void 회원의_쿠폰이_5개_미만이면_성공한다() {
            Coupon coupon = couponRepository.save(Fixture.createCoupon());
            Member member = memberRepository.save(Fixture.createMember());

            MemberCoupon memberCoupon = memberCouponService.issue(coupon, member);

            assertAll(
                    () -> assertThat(memberCoupon.getCouponId()).isEqualTo(coupon.getId()),
                    () -> assertThat(memberCoupon.getMemberId()).isEqualTo(member.getId())
            );
        }

        @Test
        void 회원의_쿠폰이_5개_이상이면_예외가_발생한다() {
            Coupon coupon = couponRepository.save(Fixture.createCoupon());
            Member member = memberRepository.save(Fixture.createMember());

            for (int i = 0; i < 5; i++) {
                memberCouponService.issue(coupon, member);
            }

            assertThatThrownBy(() -> memberCouponService.issue(coupon, member))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    class 회원_쿠폰_목록_조회 {

        @Test
        void 회원_쿠폰_조회시_발급된_쿠폰_정보를_포함한다() {
            Coupon luckyCoupon = couponRepository.save(Fixture.createCoupon("행운 쿠폰"));
            Coupon specialCoupon = couponRepository.save(Fixture.createCoupon("특별 쿠폰"));
            Member member = memberRepository.save(Fixture.createMember());

            memberCouponService.issue(luckyCoupon, member);
            memberCouponService.issue(specialCoupon, member);

            List<MemberCouponInfo> memberCoupons = memberCouponService.findAllByMember(member);

            assertAll(
                    () -> assertThat(memberCoupons).hasSize(2),
                    () -> assertThat(memberCoupons).extracting(MemberCouponInfo::couponName)
                            .containsExactlyInAnyOrder("행운 쿠폰", "특별 쿠폰")
            );
        }

        @Test
        void 발급한_회원_쿠폰이_없으면_빈_목록을_반환한다() {
            Member member = memberRepository.save(Fixture.createMember());

            List<MemberCouponInfo> memberCoupons = memberCouponService.findAllByMember(member);

            assertThat(memberCoupons).isEmpty();
        }

        @Test
        void 쿠폰_조회는_캐시를_사용한다() {
            Coupon dbCoupon = couponService.save(Fixture.createCoupon()); // service 거쳐야 발행된 쿠폰을 캐싱함
            Member member = memberRepository.save(Fixture.createMember());
            memberCouponService.issue(dbCoupon, member);

            Cache spyCache = spy(requireNonNull(cacheManager.getCache("coupon")));
            when(cacheManager.getCache("coupon")).thenReturn(spyCache);

            memberCouponService.findAllByMember(member);

            assertAll(
                    () -> verify(spyCache, times(1)).get(dbCoupon.getId()),
                    () -> verify(couponRepository, never()).findById(dbCoupon.getId())
            );
        }
    }
}
