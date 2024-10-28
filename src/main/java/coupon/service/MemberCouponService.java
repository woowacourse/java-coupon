package coupon.service;

import coupon.config.CouponCache;
import coupon.data.MemberCoupon;
import coupon.data.repository.CouponRepository;
import coupon.data.repository.MemberCouponRepository;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponMapper;
import coupon.domain.member.MemberCouponMapper;
import coupon.exception.MemberCouponIssueException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;


    @Transactional
    public void issue(long memberId, long couponId, LocalDateTime issuedDateTime) {
        List<MemberCoupon> all = memberCouponRepository.findAllByMemberIdAndCouponId(memberId, couponId);

        if (all.size() >= 5) {
            throw new MemberCouponIssueException("quantity max size exceeded");
        }

        coupon.domain.member.MemberCoupon memberCoupon = new coupon.domain.member.MemberCoupon(couponId, memberId,
                false, issuedDateTime, issuedDateTime.plusDays(6));

        memberCouponRepository.save(MemberCouponMapper.toEntity(memberCoupon));
    }


    @Transactional(readOnly = true)
    public List<CouponResponse> findByMemberId(long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberId(memberId);
        return memberCoupons.stream()
                .map(MemberCouponMapper::fromEntity)
                .map(memberCoupon -> new CouponResponse(getCachedCoupon(memberCoupon.getCouponId()), memberCoupon))
                .toList();
    }

    private Coupon getCachedCoupon(long couponId) {
        if (CouponCache.exists(couponId)) {
            return CouponMapper.fromEntity(CouponCache.get(couponId));
        }

        Optional<coupon.data.Coupon> optional = couponRepository.findById(couponId);
        if (optional.isEmpty()) {
            throw new MemberIdNotFoundException(String.format("member couponId: %d, does not exsit", couponId));
        }

        CouponCache.cache(optional.get());

        return CouponMapper.fromEntity(CouponCache.get(couponId));
    }
}
