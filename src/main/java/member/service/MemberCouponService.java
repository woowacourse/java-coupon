package member.service;

import coupon.domain.Coupon;
import coupon.repository.CouponCache;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import member.domain.MemberCoupon;
import member.dto.MemberCouponResponse;
import member.repository.MemberCouponCache;
import member.repository.MemberCouponRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCouponService {
	private static final int MAXIMUM_ISSUABLE_MEMBER_COUPON = 5;

	private final MemberCouponRepository memberCouponRepository;
	private final CouponRepository couponRepository;

	public void issue(long couponId, long memberId) {
		validateIssuable(couponId, memberId);
		MemberCoupon memberCoupon = new MemberCoupon(couponId, memberId);
		memberCouponRepository.save(memberCoupon);
		MemberCouponCache.add(memberId, memberCoupon);
	}

	private void validateIssuable(long couponId, long memberId) {
		List<MemberCoupon> memberCoupons = memberCouponRepository.findByCouponIdAndMemberId(couponId, memberId);
		if(memberCoupons.size() >= MAXIMUM_ISSUABLE_MEMBER_COUPON) {
			throw new IllegalArgumentException("최대 발급 갯수를 초과했습니다.");
		}
	}

	public List<MemberCoupon> findAllMemberCoupons(long memberId) {
		List<MemberCoupon> memberCoupons = MemberCouponCache.get(memberId);
		if (memberCoupons == null) {
			memberCoupons = memberCouponRepository.findByMemberId(memberId);
		}
		return memberCoupons;
	}

	private MemberCouponResponse generateMemberCoupon(MemberCoupon memberCoupon) {
		Coupon coupon = CouponCache.get(memberCoupon.getCouponId());
		if(coupon == null) {
			coupon = couponRepository.findById(memberCoupon.getCouponId())
				.orElseThrow(() -> new IllegalArgumentException("쿠폰이 없습니다."));
		}
		return new MemberCouponResponse(memberCoupon, coupon);
	}
}
