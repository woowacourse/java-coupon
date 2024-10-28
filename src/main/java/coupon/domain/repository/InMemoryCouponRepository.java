package coupon.domain.repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.dao.DataAccessResourceFailureException;

import coupon.domain.coupon.Coupon;

public class InMemoryCouponRepository {

    private static final Map<Long, Coupon> coupons = new ConcurrentHashMap<>();

    private InMemoryCouponRepository() {
    }

    public static void insert(final Coupon coupon) {
        if (coupons.containsKey(coupon.getId())) {
            throw new DataAccessResourceFailureException("이미 존재하는 쿠폰 정보입니다.");
        }
        coupons.put(coupon.getId(), coupon);
    }

    public static List<Coupon> getAll(final String couponName) {
        return coupons.values()
                .stream()
                .filter(coupon -> coupon.isSameName(couponName))
                .toList();
    }

    public static List<Coupon> getByMemberId(final long memberId) {
        return coupons.values()
                .stream()
                .filter(coupon -> coupon.isSameMemberId(memberId))
                .toList();
    }
}
