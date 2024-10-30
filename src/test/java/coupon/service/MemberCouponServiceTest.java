package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.fixture.CouponFixture;
import coupon.fixture.MemberFixture;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.repository.MemberRepository;

@SpringBootTest
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService sut;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CacheManager cacheManager;

    private Member member;
    private Coupon coupon1;
    private Coupon coupon2;
    private Coupon coupon3;
    private Coupon coupon4;
    private Coupon coupon5;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(MemberFixture.member);
        coupon1 = couponRepository.save(CouponFixture.coupon_1);
        coupon2 = couponRepository.save(CouponFixture.coupon_1);
        coupon3 = couponRepository.save(CouponFixture.coupon_1);
        coupon4 = couponRepository.save(CouponFixture.coupon_1);
        coupon5 = couponRepository.save(CouponFixture.coupon_1);

        var cache = cacheManager.getCache("coupon");
        cache.put(coupon1.getId(), coupon1);
        cache.put(coupon1.getId(), coupon2);
        cache.put(coupon1.getId(), coupon3);
        cache.put(coupon1.getId(), coupon4);
        cache.put(coupon1.getId(), coupon5);

        var memberCoupons = List.of(
                new MemberCoupon(member, coupon1),
                new MemberCoupon(member, coupon2),
                new MemberCoupon(member, coupon3),
                new MemberCoupon(member, coupon4),
                new MemberCoupon(member, coupon5)
        );
        memberCouponRepository.saveAll(memberCoupons);
    }

    @Nested
    class IssueMemberCoupon {

        @Test
        void success() {
            Coupon coupon2 = couponRepository.save(CouponFixture.coupon_2);
            var actual = sut.issueMemberCoupon(member, coupon2);

            assertThat(actual.getCouponId()).isEqualTo(coupon2.getId());
            assertThat(actual.getMemberId()).isEqualTo(member.getId());
        }

        @Test
        void fail() {
            assertThatThrownBy(() -> sut.issueMemberCoupon(member, coupon1))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("동일한 쿠폰은 5장까지만 발급받을 수 있습니다.");
        }
    }

    @Nested
    class FindAllCouponByMember {

        @Test
        void success() {
            var actual = sut.findAllCouponByMember(member);

            assertThat(actual).hasSize(5);
        }
    }
}
