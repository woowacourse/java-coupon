package coupon.memberCoupon.repository;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.coupon.domain.Coupon;
import coupon.member.domain.Member;
import coupon.memberCoupon.cache.MemberCouponLocalCache;
import coupon.memberCoupon.domain.MemberCoupon;
import coupon.utils.IsolatedTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MemberCouponLocalCacheTest extends IsolatedTest {

    @Autowired
    private MemberCouponLocalCache memberCouponLocalCache;

    @DisplayName("아무 데이터도 저장하지 않은 경우, 빈 Set이 반환된다.")
    @Test
    void emptySet() {
        Member member = memberRepository.save(new Member());
        Coupon coupon = couponRepository.save(new Coupon());

        assertThat(memberCouponLocalCache.get(member.getId(), coupon.getId())).isEmpty();
    }

    @DisplayName("캐시에 저장한 데이터를 조회할 수 있다.")
    @Test
    void cache() {
        Member member = memberRepository.save(new Member());
        Coupon coupon = couponRepository.save(new Coupon());
        MemberCoupon memberCoupon = MemberCoupon.create(member, coupon);

        memberCouponLocalCache.put(member.getId(), coupon.getId(), memberCoupon);

        assertThat(memberCouponLocalCache.get(member.getId(), coupon.getId())).isNotEmpty();
    }

    @DisplayName("두 개의 데이터를 저장하면 캐시의 크기는 2이다.")
    @Test
    void cacheSize() {
        Member member = memberRepository.save(new Member());
        Coupon coupon = couponRepository.save(new Coupon());
        MemberCoupon memberCoupon1 = memberCouponRepository.save(MemberCoupon.create(member, coupon));
        MemberCoupon memberCoupon2 = memberCouponRepository.save(MemberCoupon.create(member, coupon));

        memberCouponLocalCache.put(member.getId(), coupon.getId(), memberCoupon1);
        memberCouponLocalCache.put(member.getId(), coupon.getId(), memberCoupon2);

        assertThat(memberCouponLocalCache.get(member.getId(), coupon.getId())).hasSize(2);
    }
}
