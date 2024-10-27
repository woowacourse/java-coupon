package coupon.service;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import coupon.cache.CachedCoupon;
import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.exception.ErrorMessage;
import coupon.exception.GlobalCustomException;
import coupon.repository.CachedCouponRepository;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.utill.WriterDbReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCouponService {
    private static final int ISSUE_LIMIT = 5;

    private final MemberCouponRepository memberCouponRepository;
    private final CouponRepository couponRepository;
    private final WriterDbReader writerDbReader;
    private final CachedCouponRepository cachedCouponRepository;

    @Transactional
    public MemberCoupon issue(Member member, Coupon coupon) {
        if (!coupon.canIssue(LocalDateTime.now())) {
            throw new GlobalCustomException(ErrorMessage.NOT_IN_COUPON_ISSUE_PERIOD);
        }

        List<MemberCoupon> issuedMemberCoupons = memberCouponRepository.findAllByMemberAndCoupon(member, coupon);
        if (issuedMemberCoupons.size() >= ISSUE_LIMIT) {
            throw new GlobalCustomException(ErrorMessage.EXCEED_ISSUE_MEMBER_COUPON);
        }

        MemberCoupon memberCoupon = new MemberCoupon(coupon, member, false);
        return memberCouponRepository.save(memberCoupon);
    }

    @Transactional(readOnly = true)
    public List<MemberCoupon> findAllIssuedCoupons(Member member) {
        List<MemberCoupon> issuedCoupons = writerDbReader.read(
                () -> memberCouponRepository.findAllByMember(member)); // 쿠폰을 발급하자마자 조회할 것을 대비
        Set<Long> couponIds = issuedCoupons.stream()
                .map(MemberCoupon::getCouponId)
                .collect(Collectors.toSet());

        List<Coupon> coupons = findCouponsWithCache(couponIds);
        Map<Long, Coupon> couponsById = coupons.stream()
                .collect(toMap(Coupon::getId, identity()));

        issuedCoupons.forEach(memberCoupon -> {
            Coupon coupon = couponsById.get(memberCoupon.getCouponId());
            memberCoupon.loadCoupon(coupon);
        });
        return issuedCoupons;
    }

    private List<Coupon> findCouponsWithCache(Set<Long> couponIds) {
        List<Coupon> cachedCoupons = findCachedCoupons(couponIds);

        Set<Long> cachedCouponIds = cachedCoupons.stream()
                .map(Coupon::getId)
                .collect(Collectors.toSet());
        Set<Long> couponIdsNotInCache = couponIds.stream()
                .filter(id -> !cachedCouponIds.contains(id))
                .collect(Collectors.toSet());

        List<Coupon> coupons = new ArrayList<>(cachedCoupons);
        if (couponIdsNotInCache.size() > 0) {
            List<Coupon> couponsNotInCache = couponRepository.findAllByIdIn(couponIdsNotInCache);
            coupons.addAll(couponsNotInCache);
            saveToCache(couponsNotInCache);
        }
        return coupons;
    }

    private List<Coupon> findCachedCoupons(Set<Long> couponIds) {
        return couponIds.stream()
                .map(cachedCouponRepository::findById)
                .flatMap(Optional::stream)
                .map(CachedCoupon::getCoupon)
                .toList();
    }

    private void saveToCache(List<Coupon> couponsNotInCache) {
        List<CachedCoupon> cachedCoupons = couponsNotInCache.stream()
                .map(CachedCoupon::new)
                .toList();
        cachedCouponRepository.saveAll(cachedCoupons);
    }
}
