package coupon.service;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponRepository;
import coupon.domain.member.Member;
import coupon.domain.member.MemberRepository;
import coupon.domain.membercounpon.MemberCoupon;
import coupon.domain.membercounpon.MemberCouponRepository;
import coupon.executor.TransactionExecutor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    public static final String COUPON_CACHE_NAME = "couponsCache";
    private static final int MAXIMUM_COUPON_PER_MEMBER = 5;

    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final MemberRepository memberRepository;
    private final TransactionExecutor transactionExecutor;

    @CacheEvict(value = COUPON_CACHE_NAME, key = "#p0")
    @Transactional
    public Coupon create(Long memberId, Coupon coupon) {
        Member member = memberRepository.findById(memberId)
                .orElseGet(() -> getMemberWithWriteDb(memberId));

        long count = memberCouponRepository.countMemberCouponByMemberId(member.getId());
        if (count >= MAXIMUM_COUPON_PER_MEMBER) {
            throw new IllegalArgumentException("한 회원당 %d장만 쿠폰 발급이 가능합니다.".formatted(MAXIMUM_COUPON_PER_MEMBER));
        }
        Coupon savedCoupon = couponRepository.save(coupon);
        MemberCoupon memberCoupon = new MemberCoupon(savedCoupon.getId(), member.getId());
        memberCouponRepository.save(memberCoupon);
        return savedCoupon;
    }

    @Cacheable(value = COUPON_CACHE_NAME, key = "#p0")
    @Transactional(readOnly = true)
    public List<Coupon> findAllCoupons(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseGet(() -> getMemberWithWriteDb(memberId));

        List<MemberCoupon> memberCoupons = getMemberCoupons(member);
        List<Long> couponIds = memberCoupons.stream()
                .map(MemberCoupon::getCouponId)
                .toList();
        return getCoupons(couponIds);
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long id) {
        return couponRepository.findById(id)
                .orElseGet(() -> getCouponWithWriteDb(id));
    }

    private Coupon getCouponWithWriteDb(Long id) {
        return transactionExecutor.execute(() -> couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰이 존재하지 않습니다.")));
    }

    private Member getMemberWithWriteDb(Long memberId) {
        return transactionExecutor.execute(() -> memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다.")));
    }

    private List<MemberCoupon> getMemberCoupons(Member member) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberId(member.getId());
        if (memberCoupons.isEmpty()) {
            memberCoupons = transactionExecutor.execute(() -> memberCouponRepository.findAllByMemberId(member.getId()));
        }
        return memberCoupons;
    }

    private List<Coupon> getCoupons(List<Long> couponIds) {
        List<Coupon> results = couponRepository.findAllById(couponIds);
        if (results.isEmpty()) {
            results = transactionExecutor.execute(() -> couponRepository.findAllById(couponIds));
        }
        return results;
    }
}
