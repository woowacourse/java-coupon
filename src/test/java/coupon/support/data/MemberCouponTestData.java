package coupon.support.data;

import java.time.LocalDateTime;
import coupon.domain.member.Member;
import coupon.domain.membercoupon.MemberCoupon;
import coupon.domain.membercoupon.UsagePeriod;

public class MemberCouponTestData {

    public static MemberCouponBuilder defaultMemberCoupon() {
        return new MemberCouponBuilder()
                .withCouponId(1L)
                .withMember(MemberTestData.defaultMember().build())
                .withIsActive(true)
                .withUsagePeriod(new UsagePeriod(LocalDateTime.now()));

    }

    public static class MemberCouponBuilder {

        private Long id;
        private Long couponId;
        private Member member;
        private boolean isActive;
        private UsagePeriod usagePeriod;

        public MemberCouponBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public MemberCouponBuilder withCouponId(Long couponId) {
            this.couponId = couponId;
            return this;
        }

        public MemberCouponBuilder withMember(Member member) {
            this.member = member;
            return this;
        }

        public MemberCouponBuilder withIsActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public MemberCouponBuilder withUsagePeriod(UsagePeriod usagePeriod) {
            this.usagePeriod = usagePeriod;
            return this;
        }

        public MemberCoupon build() {
            return new MemberCoupon(id, couponId, member, isActive, usagePeriod);
        }
    }
}
