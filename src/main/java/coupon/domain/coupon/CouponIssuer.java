package coupon.domain.coupon;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import coupon.domain.member.Member;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CouponIssuer {

    private static final int DUPLICATION_ALLOWANCE = 5;
    private static final AtomicLong idCounter = new AtomicLong(0);

    private final Coupon coupon;
    private final Member member;

    public Coupon issue(final List<Coupon> memberCoupons) {
        if (memberCoupons.size() >= DUPLICATION_ALLOWANCE) {
            throw new IllegalArgumentException("단일 회원 동인한 쿠폰을 최대 %d까지만 소유할 수 있습니다.".formatted(DUPLICATION_ALLOWANCE));
        }
        return new Coupon(idCounter.getAndIncrement(), coupon, member.getId());
    }
}
