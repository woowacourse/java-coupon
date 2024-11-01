package member.repository;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import member.domain.MemberCoupon;

public final class MemberCouponCache {

	private static final Map<Long, List<MemberCoupon>> CACHED_MEMBER_COUPONS = new ConcurrentHashMap<>();

	public static void add(Long memberId, MemberCoupon memberCoupon) {
		List<MemberCoupon> memberCoupons = CACHED_MEMBER_COUPONS.getOrDefault(memberId, new ArrayList<>());
		memberCoupons.add(memberCoupon);
		CACHED_MEMBER_COUPONS.put(memberId, memberCoupons);
	}

	public static List<MemberCoupon> get(Long memberId) {
		return CACHED_MEMBER_COUPONS.get(memberId);
	}
}
