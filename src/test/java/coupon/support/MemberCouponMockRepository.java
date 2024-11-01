package coupon.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import coupon.domain.MemberCoupon;
import coupon.domain.MemberCouponRepository;

public class MemberCouponMockRepository implements MemberCouponRepository {

    private final AtomicLong id = new AtomicLong(1L);
    private final Map<Long, MemberCoupon> memberCoupons = new HashMap<>();

    @Override
    public MemberCoupon save(MemberCoupon memberCoupon) {
        if (memberCoupon.getId() == null) {
            Long newId = id.getAndIncrement();
            MemberCoupon newMemberCoupon = new MemberCoupon(newId, memberCoupon.getCouponId(), memberCoupon.getMemberId(), memberCoupon.isUsed(), memberCoupon.getIssuedAt());
            memberCoupons.put(newId, newMemberCoupon);
            return newMemberCoupon;
        }
        memberCoupons.put(memberCoupon.getId(), memberCoupon);
        return memberCoupon;
    }

    @Override
    public List<MemberCoupon> findAllByMemberId(long memberId) {
        return memberCoupons.values().stream()
            .filter(memberCoupon -> memberCoupon.getMemberId() == memberId)
            .collect(Collectors.toList());
    }

    @Override
    public int countByMemberIdAndCouponId(long memberId, long couponId) {
        return (int)memberCoupons.values().stream()
            .filter(memberCoupon -> memberCoupon.getMemberId() == memberId && memberCoupon.getCouponId() == couponId)
            .count();
    }
}
