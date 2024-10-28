package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.PublishedCoupon;
import coupon.repository.CouponRepository;
import coupon.repository.MemberRepository;
import coupon.repository.PublishedCouponRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MemberCouponService {

    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final PublishedCouponRepository publishedCouponRepository;

    public MemberCouponService(MemberRepository memberRepository, CouponRepository couponRepository, PublishedCouponRepository publishedCouponRepository) {
        this.memberRepository = memberRepository;
        this.couponRepository = couponRepository;
        this.publishedCouponRepository = publishedCouponRepository;
    }

    public PublishedCoupon publishCoupon(Long memberId, Long couponId) {
        List<PublishedCoupon> coupons = publishedCouponRepository.findAllByMemberIdAndCouponId(memberId, couponId);

        if (coupons.size() >= 5) {
            throw new IllegalArgumentException("Too many published coupons");
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member does not exist"));
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("Coupon does not exist"));
        return publishedCouponRepository.save(new PublishedCoupon(member, coupon, false, LocalDateTime.now()));
    }
}
