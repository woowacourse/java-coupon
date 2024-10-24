package coupon.service;

import coupon.CouponException;
import coupon.dto.CouponIssueRequest;
import coupon.dto.MemberCouponResponse;
import coupon.entity.Coupon;
import coupon.entity.MemberCoupon;
import coupon.repository.MemberCouponRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private final CouponService couponService;
    private final MemberCouponRepository memberCouponRepository;
    private final CacheManager cacheManager;

    @CachePut(key = "#result.id", value = "member_coupon")
    public MemberCoupon issue(CouponIssueRequest request) {
        Coupon coupon = couponService.read(request.couponId());
        validate(coupon);
        MemberCoupon memberCoupon = new MemberCoupon(
                request.couponId(),
                request.memberId(),
                request.start()
        );
        return memberCouponRepository.save(memberCoupon);
    }

    private void validate(Coupon coupon) {
        if (coupon.isExpired()) {
            throw new CouponException("coupon expired");
        }
    }

    public MemberCouponResponse read(long id) {
        Optional<Cache> optionalCache = Optional.ofNullable(cacheManager.getCache("member_coupon"));
        MemberCoupon memberCoupon = optionalCache.map(cache -> cache.get(id, MemberCoupon.class))
                .or(() -> memberCouponRepository.findById(id))
                .orElseThrow(() -> new CouponException("memberCoupon with id " + id + " not found"));

        Coupon coupon = couponService.read(memberCoupon.getCouponId());
        return MemberCouponResponse.from(memberCoupon, coupon);
    }
}
