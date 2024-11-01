package coupon.service;

import coupon.repository.MemberCouponRepository;
import coupon.repository.entity.Coupon;
import coupon.repository.entity.Member;
import coupon.repository.entity.MemberCoupon;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberCouponService {

    private final ReaderService readerService;
    private final CouponService couponService;
    private final MemberCouponRepository memberCouponRepository;

    @Transactional
    public MemberCoupon issueCoupon(Member member, Coupon coupon) {
        validateCount(member, coupon);
        MemberCoupon memberCoupon = new MemberCoupon(member, coupon);
        MemberCoupon saved = memberCouponRepository.save(memberCoupon);
        log.info("쿠폰 발급: {}", saved.getId());
        try {
            return readerService.read(() -> getMemberCoupon(saved.getId()));
        } catch (IllegalArgumentException e) {
            log.error("쿠폰 발급 조회 중 복제 지연 발생: {}", saved.getId());
            return getMemberCoupon(saved.getId());
        }
    }

    private void validateCount(Member member, Coupon coupon) {
        if (memberCouponRepository.countByMemberAndCoupon(member, coupon) >= 5) {
            throw new IllegalArgumentException("한 명의 회원은 동일한 쿠폰을 최대 5장까지 발급할 수 있습니다.");
        }
    }

    private MemberCoupon getMemberCoupon(Long memberCouponId) {
        log.info("쿠폰 발급 정보 조회: {}", memberCouponId);
        return memberCouponRepository.findById(memberCouponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰 발급 정보입니다: %d".formatted(memberCouponId)));
    }

    @Transactional
    public List<MemberCoupon> getMemberCoupons(Member member) {
        return readerService.read(() -> {
            List<MemberCoupon> memberCoupons = memberCouponRepository.findByMember(member);
            memberCoupons.forEach(mc -> mc.setCoupon(couponService.getCoupon(mc.getCoupon().getId())));
            return memberCoupons;
        });
    }
}
