package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.domain.repository.CouponRepository;
import coupon.domain.repository.MemberCouponRepository;
import coupon.domain.repository.MemberRepository;
import jakarta.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final MemberRepository memberRepository;
    private final CacheManager cacheManager;

    private CouponService self;

    @Autowired
    @Lazy
    public void setSelf(CouponService self) {
        this.self = self;
    }

    @Transactional
    public void createCoupon(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "coupons", key = "#couponId")
    public Coupon getCoupon(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElse(null);
        if (coupon == null) {
            return self.getCouponFromWriter(couponId);
        }
        return coupon;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "memberCoupons", key = "#memberId")
    public List<MemberCoupon> getMemberCoupons(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        List<MemberCoupon> memberCoupons = memberCouponRepository.findByMember(member);
        if (memberCoupons.isEmpty()) {
            return self.getMemberCouponsFromWriter(member);
        }
        return memberCoupons;
    }

    @Transactional
    public List<MemberCoupon> getMemberCouponsFromWriter(Member member) {
        return memberCouponRepository.findByMember(member);
    }

    @Transactional
    public Coupon getCouponFromWriter(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰이 존재하지 않습니다."));
    }

    @Transactional
    @CachePut(value = "memberCoupons", key = "#memberId")
    public void issueCouponToMember(Long memberId, Long couponId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰이 존재하지 않습니다."));

        if (member.getCouponCount() >= 5) {
            throw new IllegalArgumentException("한 회원은 최대 5장의 쿠폰만 발급받을 수 있습니다.");
        }

        coupon.decrementAvailableCount();
        member.incrementCouponCount();
        MemberCoupon memberCoupon = new MemberCoupon(coupon, member, LocalDateTime.now(), LocalDateTime.now().plusDays(7));
        memberCouponRepository.save(memberCoupon);

        cacheManager.getCache("memberCoupons").put(memberId, memberCouponRepository.findByMember(member));
    }

    @Transactional
    @CacheEvict(value = "coupons", key = "#couponId")
    public void updateCoupon(Long couponId, Coupon updatedCoupon) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰이 존재하지 않습니다."));

        coupon.setName(updatedCoupon.getName());
        coupon.setDiscountAmount(updatedCoupon.getDiscountAmount());
        coupon.setMinimumOrderAmount(updatedCoupon.getMinimumOrderAmount());
        coupon.setCategory(updatedCoupon.getCategory());
        coupon.setStartDate(updatedCoupon.getStartDate());
        coupon.setEndDate(updatedCoupon.getEndDate());
        coupon.setAvailableCount(updatedCoupon.getAvailableCount());

        couponRepository.save(coupon);
    }
}

