package coupon.support.data;

import java.time.LocalDateTime;
import coupon.membercoupon.domain.MemberCoupon;

public class MemberCouponTestData {

    public static MemberCouponBuilder defaultMemberCoupon() {
        return new MemberCouponBuilder()
                .withId(null)
                .withCouponId(1L)
                .withMemberId(1L)
                .withUsed(false)
                .withIssuedAt(LocalDateTime.now())
                .withExpiredAt(LocalDateTime.now());
    }

    public static class MemberCouponBuilder {

        private Long id;
        private Long couponId;
        private Long memberId;
        private boolean used;
        private LocalDateTime issuedAt;
        private LocalDateTime expiredAt;

        public MemberCouponBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public MemberCouponBuilder withCouponId(Long couponId) {
            this.couponId = couponId;
            return this;
        }

        public MemberCouponBuilder withMemberId(Long memberId) {
            this.memberId = memberId;
            return this;
        }

        public MemberCouponBuilder withUsed(boolean used) {
            this.used = used;
            return this;
        }

        public MemberCouponBuilder withIssuedAt(LocalDateTime issuedAt) {
            this.issuedAt = issuedAt;
            return this;
        }

        public MemberCouponBuilder withExpiredAt(LocalDateTime expiredAt) {
            this.expiredAt = expiredAt;
            return this;
        }

        public MemberCoupon build() {
            return new MemberCoupon(id, couponId, memberId, used, issuedAt, expiredAt);
        }
    }
}
