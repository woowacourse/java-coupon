package coupon.membercoupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.service.CouponService;
import coupon.member.repository.MemberRepository;
import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.domain.dto.MemberCouponItem;
import coupon.membercoupon.repository.MemberCouponRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberCouponService {

    private static final int MAX_ISSUE_COUNT = 5;

    private final CouponService couponService;
    private final MemberRepository memberRepository;
    private final MemberCouponRepository memberCouponRepository;

    public MemberCouponService(CouponService couponService,
                               MemberRepository memberRepository,
                               MemberCouponRepository memberCouponRepository) {
        this.couponService = couponService;
        this.memberRepository = memberRepository;
        this.memberCouponRepository = memberCouponRepository;
    }

    @Transactional
    public void create(Long couponId, Long memberId) {
        validateCouponAndMember(couponId, memberId);
        MemberCoupon memberCoupon = new MemberCoupon(couponId, memberId, LocalDateTime.now());
        memberCouponRepository.save(memberCoupon);
    }

    private void validateCouponAndMember(Long couponId, Long memberId) {
        validateCoupon(couponId);
        validateMember(memberId);
        int issuedCount = memberCouponRepository.countByCouponIdAndMemberId(couponId, memberId);
        if (issuedCount >= MAX_ISSUE_COUNT) {
            throw new IllegalArgumentException("더 이상 발급할 수 없는 쿠폰입니다. 최대 발급 횟수 : " + MAX_ISSUE_COUNT);
        }
    }

    private void validateCoupon(Long couponId) {
        Coupon coupon = couponService.getCoupon(couponId);
        if (!coupon.getIssuablePeriod().canIssue(LocalDateTime.now())) {
            throw new IllegalArgumentException("발급 가능한 날짜가 아닙니다.");
        }
    }

    private void validateMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
    }

    @Transactional
    public List<MemberCouponItem> getAllMemberCouponByMemberId(Long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findByMemberId(memberId);
        List<MemberCouponItem> memberCouponItems = new ArrayList<>();
        for (MemberCoupon memberCoupon : memberCoupons) {
            Coupon coupon = couponService.getCoupon(memberCoupon.getCouponId());
            memberCouponItems.add(new MemberCouponItem(memberCoupon, coupon));
        }
        return memberCouponItems;
    }
}
