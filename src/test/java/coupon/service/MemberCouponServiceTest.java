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
import coupon.dto.request.CouponSaveRequest;
import coupon.dto.response.MemberCouponInfo;
import coupon.repository.CouponRepository;
import coupon.repository.MemberRepository;
import coupon.support.Fixture;
import java.time.LocalDateTime;
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
    private MemberRepository memberRepository;

    @SpyBean
    private CouponRepository couponRepository;

    @SpyBean
    private CacheManager cacheManager;

    @Autowired
    private CouponService couponService;

    @Nested
    class 회원_쿠폰_발급 {

        @Test
        void 존재하지_않는_coupon_이면_예외가_발생한다() {
            long invalidCouponId = 0;
            long validMemberId = memberRepository.save(Fixture.createMember()).getId();

            assertThatThrownBy(() -> memberCouponService.issue(invalidCouponId, validMemberId))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("존재하지 않는 couponId 입니다.");
        }

        @Test
        void 존재하지_않는_member_이면_예외가_발생한다() {
            long validCouponId = couponRepository.save(Fixture.createCoupon()).getId();
            long invalidMemberId = 0;

            assertThatThrownBy(() -> memberCouponService.issue(validCouponId, invalidMemberId))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("존재하지 않는 memberId 입니다.");
        }

        @Test
        void 회원의_쿠폰이_5개_미만이면_성공한다() {
            Coupon coupon = couponRepository.save(Fixture.createCoupon());
            Member member = memberRepository.save(Fixture.createMember());

            MemberCoupon memberCoupon = memberCouponService.issue(coupon.getId(), member.getId());

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
                memberCouponService.issue(coupon.getId(), member.getId());
            }

            assertThatThrownBy(() -> memberCouponService.issue(coupon.getId(), member.getId()))
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

            memberCouponService.issue(luckyCoupon.getId(), member.getId());
            memberCouponService.issue(specialCoupon.getId(), member.getId());

            List<MemberCouponInfo> memberCoupons = memberCouponService.findAllByMemberId(member.getId());

            assertAll(
                    () -> assertThat(memberCoupons).hasSize(2),
                    () -> assertThat(memberCoupons).extracting(MemberCouponInfo::couponName)
                            .containsExactlyInAnyOrder("행운 쿠폰", "특별 쿠폰")
            );
        }

        @Test
        void 발급한_회원_쿠폰이_없으면_빈_목록을_반환한다() {
            Member member = memberRepository.save(Fixture.createMember());

            List<MemberCouponInfo> memberCoupons = memberCouponService.findAllByMemberId(member.getId());

            assertThat(memberCoupons).isEmpty();
        }

        @Test
        void 쿠폰_조회는_캐시를_사용한다() {
            CouponSaveRequest request = new CouponSaveRequest("반짝 쿠폰", 1000L, 30000L, "가전", LocalDateTime.now(),
                    LocalDateTime.now().plusDays(1));
            Coupon dbCoupon = couponService.save(request);
            Member member = memberRepository.save(Fixture.createMember());

            memberCouponService.issue(dbCoupon.getId(), member.getId());

            Cache spyCache = spy(requireNonNull(cacheManager.getCache("coupon")));
            when(cacheManager.getCache("coupon")).thenReturn(spyCache);

            memberCouponService.findAllByMemberId(member.getId());

            assertAll(
                    () -> verify(spyCache, times(1)).get(dbCoupon.getId()),
                    () -> verify(couponRepository, never()).findById(dbCoupon.getId())
            );
        }
    }
}
