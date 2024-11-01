package coupon.service;

import coupon.domain.MemberCoupon;
import coupon.dto.response.FindMyCouponsResponse;
import coupon.repository.MemberCouponRepository;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.framework.AopContext;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberCouponQueryService {

    private final MemberCouponRepository memberCouponRepository;
    private final CouponQueryService couponQueryService;

    public List<FindMyCouponsResponse> findByMemberId(long memberId) {
        MemberCouponQueryService proxy = (MemberCouponQueryService) AopContext.currentProxy();
        List<MemberCoupon> cachedMemberCoupons = proxy.findAllByMemberIdFromCache(memberId);

        if (cachedMemberCoupons.isEmpty()) {
            List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberId(memberId);
            return toEntity(memberCoupons);
        }

        return toEntity(cachedMemberCoupons);
    }

    @Cacheable(value = "memberCoupons", key = "#memberId")
    public List<MemberCoupon> findAllByMemberIdFromCache(long memberId) {
        return Collections.unmodifiableList(Collections.emptyList());
    }

    private List<FindMyCouponsResponse> toEntity(List<MemberCoupon> memberCoupons) {
        return memberCoupons.stream()
                .map(memberCoupon -> new FindMyCouponsResponse(
                        couponQueryService.findById(memberCoupon.getCouponId()), memberCoupon))
                .toList();
    }
}
