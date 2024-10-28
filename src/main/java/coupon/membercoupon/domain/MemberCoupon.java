package coupon.membercoupon.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import coupon.coupon.domain.Coupon;
import coupon.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberCoupon {

    private static final int MAXIMUM_ENABLE_ISSUE_MEMBER_COUPON_COUNT = 5;

    private final Long id;
    private final Member member;
    private final Coupon coupon;
    private final boolean isUsed;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime expiredAt;

    public static boolean isCouponLimitReached(final int currentIssuesMemberCouponCount) {
        return currentIssuesMemberCouponCount >= MAXIMUM_ENABLE_ISSUE_MEMBER_COUPON_COUNT;
    }

    public static MemberCoupon create(
            final Member member,
            final Coupon coupon,
            final boolean isUsed
    ) {
        final LocalDateTime now = LocalDateTime.now();
        return new MemberCoupon(
                null,
                member,
                coupon,
                isUsed,
                now,
                Coupon.calculateCouponExpiredAt(now)
        );
    }

    public MemberCoupon(
            final Long id,
            final Member member,
            final Coupon coupon,
            final boolean isUsed,
            final LocalDateTime createdAt,
            final LocalDateTime expiredAt
    ) {
        validateMember(member);
        validateCoupon(coupon);
        validateCreatedAt(createdAt);
        validateExpiredAt(expiredAt);

        this.id = id;
        this.member = member;
        this.coupon = coupon;
        this.isUsed = isUsed;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }

    private void validateMember(final Member member) {
        if (member == null) {
            throw new IllegalArgumentException("회원 정보로 null을 입력할 수 없습니다.");
        }
    }

    private void validateCoupon(final Coupon coupon) {
        if (coupon == null) {
            throw new IllegalArgumentException("쿠폰 정보로 null을 입력할 수 없습니다.");
        }
    }

    private void validateCreatedAt(final LocalDateTime createdAt) {
        if (createdAt == null) {
            throw new IllegalArgumentException("회원 쿠폰 생성 일시로 null을 입력할 수 없습니다.");
        }
    }

    private void validateExpiredAt(final LocalDateTime expiredAt) {
        if (expiredAt == null) {
            throw new IllegalArgumentException("회원 쿠폰 만료 일시로 null을 입력할 수 없습니다.");
        }
    }

//    public Long getId() {
//        return id;
//    }

    public Long giveMemberId() {
        return member.getId();
    }

    public Long giveCouponId() {
        return coupon.getId();
    }

    public boolean getIsUsed() {
        return isUsed;
    }

    //    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public LocalDateTime getExpiredAt() {
//        return expiredAt;
//    }
}
