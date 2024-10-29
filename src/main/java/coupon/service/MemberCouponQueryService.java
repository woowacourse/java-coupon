package coupon.service;

import coupon.domain.MemberCoupon;
import coupon.dto.response.FindMyCouponsResponse;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberCouponQueryService {

    private final MemberCouponRepository memberCouponRepository;
    private final CouponRepository couponRepository;

    @Cacheable(value = "coupons", key = "#memberId")
    public List<FindMyCouponsResponse> findByMemberId(long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberId(memberId);
        return memberCoupons.stream()
                .map(memberCoupon -> new FindMyCouponsResponse(
                        couponRepository.getByCouponId(memberCoupon.getCouponId()), memberCoupon))
                .collect(Collectors.toList());
    }
}
