package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.MemberCoupon;
import coupon.dto.FindMemberCouponsDto;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CouponQueryService {

    public static final String CACHE_MANAGER = "redisCacheManager";

    private final CouponRepository couponRepository;
    private final CouponCommandService couponCommandService;
    private final MemberCouponRepository memberCouponRepository;

    public CouponQueryService(CouponRepository couponRepository, CouponCommandService couponCommandService,
                              MemberCouponRepository memberCouponRepository) {
        this.couponRepository = couponRepository;
        this.couponCommandService = couponCommandService;
        this.memberCouponRepository = memberCouponRepository;
    }

    @Cacheable(value = "coupon", key = "#id", cacheManager = CACHE_MANAGER)
    public Coupon getCoupon(Long id) {
        return couponRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));
    }

    @Cacheable(value = "private_coupons", key = "#memberId", cacheManager = CACHE_MANAGER)
    public List<FindMemberCouponsDto> getCoupons(Long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberId(memberId);

        return memberCoupons.stream()
                .map(memberCoupon -> {
                    Coupon coupon = getCoupon(memberCoupon.getId());
                    return new FindMemberCouponsDto(memberCoupon, coupon);
                })
                .toList();
    }
}
