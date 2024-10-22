package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.MemberCoupon;
import coupon.domain.repository.MemberCouponRepository;
import coupon.service.dto.MemberCouponDto;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberCouponService {

    private static final int MAX_MEMBER_COUPON_COUNT = 5;

    private MemberCouponRepository memberCouponRepository;
    private CouponLookupService couponLookupService;

    @Transactional
    public void create(MemberCoupon memberCoupon) {
        validate(memberCoupon);
        memberCouponRepository.save(memberCoupon);
    }

    private void validate(MemberCoupon memberCoupon) {
        validateCoupon(memberCoupon.getCouponId());
        validateMemberIssueLimit(memberCoupon);
    }

    private void validateCoupon(Long couponId) {
        couponLookupService.findById(couponId);
    }

    private void validateMemberIssueLimit(MemberCoupon memberCoupon) {
        Long memberId = memberCoupon.getMemberId();
        Long couponId = memberCoupon.getCouponId();

        long memberCouponCount = memberCouponRepository.countByMemberIdAndCouponId(memberId, couponId);

        if (memberCouponCount >= MAX_MEMBER_COUPON_COUNT) {
            throw new IllegalArgumentException("한 명의 회원은 동일한 쿠폰을 최대 5장까지 발급할 수 있습니다.");
        }
    }

    public List<MemberCouponDto> findByMemberId(Long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberId(memberId);
        return toMemberCouponDtos(memberCoupons);
    }

    private List<MemberCouponDto> toMemberCouponDtos(List<MemberCoupon> memberCoupons) {
        Map<Long, Coupon> couponsWithId = getCouponsWithId(memberCoupons);
        return convertToMemberCouponDtos(memberCoupons, couponsWithId);
    }

    private Map<Long, Coupon> getCouponsWithId(List<MemberCoupon> memberCoupons) {
        return couponLookupService.findByMemberCoupons(memberCoupons)
                .stream()
                .collect(Collectors.toMap(Coupon::getId, Function.identity()));
    }

    private List<MemberCouponDto> convertToMemberCouponDtos(List<MemberCoupon> memberCoupons, Map<Long, Coupon> coupons) {
        return memberCoupons.stream()
                .map(memberCoupon -> {
                    Coupon coupon = coupons.get(memberCoupon.getCouponId());
                    return MemberCouponDto.from(memberCoupon, coupon);
                })
                .toList();
    }
}
