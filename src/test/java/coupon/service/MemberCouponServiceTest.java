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
import org.springframework.test.context.jdbc.Sql;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.fixture.CouponFixture;
import coupon.fixture.MemberFixture;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.repository.MemberRepository;

@SpringBootTest
@Sql("classpath:init.sql")
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

    @BeforeEach
    void setUp() {
        member = memberRepository.save(MemberFixture.member);
        coupon1 = couponRepository.save(CouponFixture.coupon_1);

        var cache = cacheManager.getCache("coupon");
        cache.put(coupon1.getId(), coupon1);

        var memberCoupons = List.of(
                new MemberCoupon(member, coupon1),
                new MemberCoupon(member, coupon1),
                new MemberCoupon(member, coupon1),
                new MemberCoupon(member, coupon1),
                new MemberCoupon(member, coupon1)
        );
        memberCouponRepository.saveAll(memberCoupons);
    }

    @Nested
    class IssueMemberCoupon {

        @Test
        void success() {
            Coupon newCoupon = couponRepository.save(CouponFixture.coupon_2);
            var actual = sut.issueMemberCoupon(member, newCoupon);

            assertThat(actual.getCouponId()).isEqualTo(newCoupon.getId());
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

        @Test
        void delayTest() {
            Coupon newCoupon = couponRepository.save(CouponFixture.coupon_2);
            sut.issueMemberCoupon(member, newCoupon);

            var actual = sut.findAllCouponByMember(member);

            assertThat(actual).hasSize(6);
        }
    }
}
